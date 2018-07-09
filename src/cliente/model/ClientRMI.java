package cliente.model;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;


import base.model.Part;
import base.model.Part.Componente;
import base.model.PartImplementation;
import base.model.PartRepository;

import servidor.model.RemoteRepo;
import view.AutoWiredCommands;
import base.model.Mensagens;

public class ClientRMI extends AbstractClient {

	
	private Registry registry;
	
	@Override
	protected void prepareRepositories() {
		try {
			registry = LocateRegistry.getRegistry();
		} catch (RemoteException e) {
			System.err.println(Mensagens.CONEXAO_ERRO_LOCATE.texto);
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	@Override
	public void connect(String repositorio) {
		repositorioAtual = repositorio;
		try {
			repositorioRemoto = (PartRepository) registry.lookup(repositorio);
			System.out.println(Mensagens.CONEXAO_SUCESSO.texto);
		} catch (NotBoundException e) {
			System.err.println(Mensagens.CONEXAO_ERRO_LOOKUP_NOT_BOUND.texto+repositorio);
			
		} catch (RemoteException e) {
			System.err.println(Mensagens.CONEXAO_ERRO_REMOTE.texto+repositorio);
			System.err.println(e.toString());
		}
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
	
	@Override
	public void repoList() {
		final String[] repositorios;
		System.out.println("== Obtendo lista de servidores ==");
		try {
			 repositorios = registry.list();
			 System.out.println(util.StringUtils.arrayToPrintableList(repositorios));
		} catch (RemoteException e) {
			System.err.println(Mensagens.REPO_LIST_ERRO_REMOTE.texto);
			System.err.println(e.toString());
		}
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
	

}
