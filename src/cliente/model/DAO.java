package cliente.model;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Map;

import base.model.Mensagens;
import base.model.Part;
import base.model.PartRepository;

public class DAO {

	protected PartRepository repositorioRemoto;
	
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
	
	private boolean repositorioRemotoInvalido() {
		if(!repositorioRemotoEhValido(repositorioRemoto)) {
			System.err.println(Mensagens.REMOTE_REPO_ERRO_NAO_CONECTADO.texto);
			return true;
		};
		return false;
	}

	
	public boolean repositorioRemotoEhValido(PartRepository repositorio) { 
		if(repositorio == null)
		{
			return false;
		}
		return true;
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
}
