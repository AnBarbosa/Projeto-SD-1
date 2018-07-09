package cliente.dao;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Map;

import base.model.interfaces.Part;
import base.model.interfaces.PartRepository;
import cliente.view.auxiliar.Mensagens;

public class RepositoryDAO {
	private PartRepository repositorioRemoto;
	
	public void setRepositorio(PartRepository r) { repositorioRemoto = r; }
	
	public Map<Part, Integer> getMap() {
		 Map<Part, Integer> mapaPartes = Collections.emptyMap();

		 if(repositorioRemotoInvalido()) {
			 return mapaPartes;
		 }
		 
		try {
			mapaPartes = repositorioRemoto.getComponentsMap();
		} catch (RemoteException e) {
			System.out.println("[getMap: ]"+Mensagens.CONEXAO_ERRO_REMOTE.texto);
		}
		
		return mapaPartes;
	
	}

	public Part getPart(String code) {
		if(repositorioRemotoInvalido()) {
			 return null;
		 }
		
		Part p = null;
		try {
			p = repositorioRemoto.getPart(code);
			
		} catch (RemoteException e) {
				System.out.println("[getPart]: "+Mensagens.ERROR_REMOTE_EXCEPTION.texto);
		} 
		return p;
	}
	
	private boolean repositorioRemotoInvalido() {
		if(repositorioRemoto==null) {
			System.out.println(Mensagens.REMOTE_REPO_ERRO_NAO_CONECTADO.texto);
			return true;
		}
		return false;
	}

	public boolean repositorioRemotoEhValido(PartRepository repositorio) { 
		if(repositorio == null)
		{
			return false;
		}
		return true;
	}
	
}
