package base.model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class PartContainer implements PartRepository, Serializable {

	private final String nome;
	private Map<Part, Integer> pecas;
	
	public PartContainer(String serverName) {
		this.nome = serverName;
		pecas = new HashMap<Part, Integer>();
	}

	public PartContainer(PartContainer container) {
		this.nome = container.nome;
		this.pecas = new HashMap<>(container.pecas);
	}

	@Override
	public String getName() {
		return nome;
	}

	@Override
	public long addPart(Part peca) {
		if(pecas.containsKey(peca))
		{
			int qtdeAtual = pecas.get(peca);
			pecas.put(peca, qtdeAtual+1);
		} 
		else
		{
			pecas.put(peca, 1);
		}
		return peca.getCode();
		
		
	}

	@Override
	public long getNumberOfParts(boolean distintas) {
		if(distintas) {
			return (long) pecas.keySet().stream().count();
		}
		else {
			IntStream intstream = pecas.values().stream().mapToInt(Number::intValue);
			return intstream.sum();
		}
	}

	@Override
	public Map<Part, Integer> getComponentsMap()  {
		return new HashMap<>(pecas);
	}

	public void clear() {
		pecas.clear();
	}

	@Override
	public Part getPart(long code) throws RemoteException {
		for(Part p : pecas.keySet()) {
			if(p.getCode() == code) {
				return p;
			}
		}
		return null;
	}
	
	
	

}
