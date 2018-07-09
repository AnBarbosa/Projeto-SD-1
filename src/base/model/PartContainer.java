package base.model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public class PartContainer implements PartRepository, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	public String addPart(Part peca) {
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
	public Part getPart(String code) throws RemoteException {
		for(Part p : pecas.keySet()) {
			if(p.getCode().equals(code)) {
				return p;
			}
		}
		return null;
	}
	
	public boolean equals(Object cont)
	{
		if(cont == null) 						{			return false;}
		if(!(cont  instanceof PartContainer)) 	{ return false; }

		PartContainer b = (PartContainer) cont;
		if(!(this.nome.equals(b.nome))) { return false;}
		
		Set<Part> bSet = b.pecas.keySet();
		Set<Part> thisSet = this.pecas.keySet();
		if(thisSet.equals(bSet)) {
			for(Part pt : thisSet) {
				if(this.pecas.get(pt) != b.pecas.get(pt)) { 
					return false;
				}
			}
		} else {
			return false;
		}

		return true;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		Set<Part> thisSet = this.pecas.keySet();
		for(Part pt : thisSet) {
			result = 31 * result + pt.getCode().hashCode();
		}
		return result;
	}

}
