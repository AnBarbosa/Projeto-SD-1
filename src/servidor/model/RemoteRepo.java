package servidor.model;

import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import base.model.Part;
import base.model.PartRepository;

public class RemoteRepo implements PartRepository {

	String nome;
	List<Part> partes; 
	int pecasNoRepositorio = 1;
	
	public RemoteRepo(String nome) {
		this.nome = nome;
		partes = new CopyOnWriteArrayList<Part>();
	}

	@Override
	public String getName() throws RemoteException {
		return nome;
	}

	@Override
	public String addPart(Part peca) throws RemoteException {
		if(peca.getCode()==null || peca.getCode().equals(""))
		{
			peca.setCode(getNextCode());
		} 
		// Se essa peça tiver o mesmo código que outra no repositório,
		// mas for diferente, então são peças diferentes, mudamos seu código.
		if(diferenteDeParteComMesmoCodigo(peca)) {
			peca.setCode(getNextCode());
		}
		peca.setRepositorioDeOrigem(this.nome);
		partes.add(peca);
		
		return peca.getCode();
		
	}
	
	private boolean diferenteDeParteComMesmoCodigo(Part peca) {
		try {
			String codigo = peca.getCode();
			Part noRepositorio;
			noRepositorio = getPart(codigo);
			if(noRepositorio==null || peca.equals(noRepositorio)) {
				return false;
			} 
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	private String getNextCode() {
		String uidStr = nome+(pecasNoRepositorio++); 
		return uidStr;
	}

	@Override
	public long getNumberOfParts(boolean distintas) throws RemoteException {
		return partes.size();
	}

	@Override
	public Map<Part, Integer> getComponentsMap() throws RemoteException {
		Map<Part, Integer> mapa = new HashMap<>();
		for(Part part : partes) {
			if(mapa.containsKey(part)) {
				int qtdeAtual = mapa.get(part);
				mapa.replace(part, qtdeAtual+1);
			} else {
				mapa.put(part,  1);
			}
		}
		
		return mapa;
	}

	@Override
	public Part getPart(String code) throws RemoteException {
		Iterator<Part> iter = partes.iterator();
		while(iter.hasNext()) {
			Part parte = iter.next();
			if(parte.getCode().equals(code)) {
				//System.err.printf("[serv] parte encontrada: %s\n", parte);
				return parte;
			}
		}
		return null;
	}

}
