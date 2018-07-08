package servidor.model;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import base.model.Part;
import base.model.PartRepository;

public class RepositorioRemoto implements PartRepository {

	public RepositorioDePecas repo;
	
	public static void main(String args[]) {
		if(args.length == 0) {
			System.err.println("Todo servidor deve ser inicializado com um nome.");
			System.exit(1);
	 	}
		String nomeDoServidor = args[0];	
		
		RepositorioRemoto thisRepo = new RepositorioRemoto(nomeDoServidor);
		thisRepo.putOnline();
	}
	
	public RepositorioRemoto(String nome) {
		repo = new RepositorioDePecas(nome);
	}
	
	
	public void putOnline() {

		try {
			PartRepository stub = (PartRepository) UnicastRemoteObject.exportObject(repo, 0);
			Registry registry = LocateRegistry.getRegistry();
			registry.bind(repo.getName(), stub);
			System.out.println("Servidor "+repo.getName()+" adicionado ao rmiregistry.");
			
		} catch (Exception e) {
			System.err.println("Houve um erro no servidor: "+e.toString());
			System.exit(1);
		}
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
		return repo.getName();
	}

	@Override
	public void addPart(Part peca) {
		repo.addPart(peca);
	}

	@Override
	public int getNumberOfParts(boolean distintas) {
		return repo.getNumberOfParts(distintas);
	}

}
