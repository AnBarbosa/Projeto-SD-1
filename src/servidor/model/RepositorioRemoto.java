package servidor.model;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import base.model.Part;
import base.model.PartContainer;
import base.model.PartRepository;

public class RepositorioRemoto implements PartRepository {

	public PartContainer repo;
	
	private static Map<String, Long> prefixoRepositorio = new ConcurrentHashMap<>();
	private static Map<String, Long> lastPartCode = new ConcurrentHashMap<>();
	
	public static void main(String args[]) {
		if(args.length == 0) {
			System.err.println("Todo servidor deve ser inicializado com um nome.");
			System.exit(1);
	 	}
		String nomeDoServidor = args[0];	
		
		RepositorioRemoto thisRepo = new RepositorioRemoto(nomeDoServidor);
		thisRepo.start();
		
	}
	
	public RepositorioRemoto(String nome) {
		repo = new PartContainer(nome);
	}
	
	public void start() {
		this.prefixoRepositorio.put(repo.getName(), (long) repo.getName().hashCode());
		this.lastPartCode.put(repo.getName(), 0L);
		bindServer();
		setShutdownHook();
	}
	public void bindServer() {
		System.out.println("Binding server...");
		try {
			PartRepository stub = (PartRepository) UnicastRemoteObject.exportObject(repo, 0);
			Registry registry = LocateRegistry.getRegistry();
			registry.bind(repo.getName(), stub);
			
			System.out.println("Servidor "+repo.getName()+" adicionado ao rmiregistry.");
			
		} catch (Exception e) {
			System.err.println("Houve um erro no servidor: "+e.toString());
			System.exit(1);
		}
		
	
	}
	
	private void setShutdownHook() {
		System.out.println("Preparando shutdown-hook...");
		Runtime.getRuntime().addShutdownHook(new Thread(
				()-> {
					System.out.println("Saindo");
					try {
						Registry reg = LocateRegistry.getRegistry();
						reg.unbind(repo.getName());
						System.out.println(repo.getName() + " removido do rmiregistry.");
					} catch (Exception e) {
						System.err.println("Houve um erro ao desregistrar o servidor "+repo.getName());
						e.printStackTrace();
					} 
				}));
		System.out.println("Adicionado hook de saida.");
	}
	
	@Override
	public String getName() {
		System.out.println("Solicitado nome do repositório. Retornando "+repo.getName());
		return repo.getName();
	}

	@Override
	public long addPart(Part peca) {
		System.out.printf("Adicionando peça %s ao repositório.\n", repo.getName());
		long codigo = peca.getCode();
		if(codigo==0) {
			codigo = getNextCode();
			peca.setCode(codigo);
		}
		repo.addPart(peca);
		return codigo;
	}

	@Override
	public long getNumberOfParts(boolean distintas) {
		long numeroDePartes = repo.getNumberOfParts(distintas);
		System.out.printf("Retornando numero de partes presentes no repositório. [%d]\n", numeroDePartes);
		return numeroDePartes;
	}

	@Override
	public Map<Part, Integer> getComponentsMap() throws RemoteException {
		System.out.printf("Retornando o mapa de componentes desse repositorio.\n");
		return repo.getComponentsMap();
	}

	@Override
	public Part getPart(long code) throws RemoteException {
		return repo.getPart(code);
	}
	
	private int getNextCode() {
		System.out.println("Obtendo próximo código...");
		
		long prefixo = prefixoRepositorio.get(repo.getName());
		long partePeca = lastPartCode.get(repo.getName());
		String pref = String.valueOf(prefixo);
		String partCode = String.valueOf(partePeca);
		String dual = pref+partCode;

		lastPartCode.replace(repo.getName(), ++partePeca);
		System.out.printf("Obtendo próximo código. Pref(%s) LastPart(%s)...(%s)\n", pref, partCode, dual);
		System.out.printf("Obtendo próximo código int: (%d)...\n", Integer.parseInt(dual));
		int valor;
		try {
			valor = Integer.parseInt(dual);
		} catch (Exception e) {
			System.err.println("Numero máximo de elementos atingidos para esse repositório.");
			valor = -1;
		}
		System.out.println("Próximo código é "+valor);
		return valor;
		
	}

}
