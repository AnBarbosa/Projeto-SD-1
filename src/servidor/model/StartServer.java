package servidor.model;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

import base.model.interfaces.Part;
import base.model.interfaces.PartRepository;

public class StartServer implements PartRepository {

	private RemoteRepo repo;
	
	public StartServer(String nome) {
		repo = new RemoteRepo(nome);
	}
	
	public static void main(String[] args) {
		StartServer ss = new StartServer(getNomeFromArgs(args));
		ss.start();

	}
	
	private void start() {
		connectRMI();
		setShutdownRoutine();
	}
	
	private void connectRMI() {
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
	
	private void setShutdownRoutine() {
		System.out.print("Preparando shutdown-hook..." );
		Runtime.getRuntime().addShutdownHook(new Thread(
				()-> {
					System.out.println("Saindo");
					try {
						Registry reg = LocateRegistry.getRegistry();
						reg.unbind(repo.getName());
						System.out.println(repo.getName() + " removido do rmiregistry.");
					} catch (Exception e) {
						try {
							System.err.println("Houve um erro ao desregistrar o servidor "+repo.getName());
						} catch (RemoteException e1) {
							// Should never happens - Variavel  Local
							e1.printStackTrace();
						}
						e.printStackTrace();
					} 
				}));
		System.out.println("Concluído.");
	}

	private static String getNomeFromArgs(String [] args) {
		if(args.length == 0) {
			System.err.println("Todo servidor deve ser inicializado com um nome.");
			System.exit(1);
	 	}
		String nomeDoServidor = args[0];
		return nomeDoServidor;
	}

	@Override
	public String getName() throws RemoteException {
		return repo.getName();
	}

	@Override
	public String addPart(Part peca) throws RemoteException {
		return repo.addPart(peca);
	}

	@Override
	public long getNumberOfParts(boolean distintas) throws RemoteException {
		return repo.getNumberOfParts(distintas);
	}

	@Override
	public Map<Part, Integer> getComponentsMap() throws RemoteException {
		return repo.getComponentsMap();
	}

	@Override
	public Part getPart(String code) throws RemoteException {
		return repo.getPart(code);
	}

}
