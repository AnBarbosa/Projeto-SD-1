package servidor.model;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import base.model.Part;
import base.model.PartRepository;

public class RepositorioDePecas implements PartRepository {

	private final String nome;
	private final Map<Part, Integer> pecas;
	
	public RepositorioDePecas(String serverName) {
		this.nome = serverName;
		pecas = new HashMap<Part, Integer>();
	}

	@Override
	public String getName() {
		return nome;
	}

	@Override
	public void addPart(Part peca) {
		if(pecas.containsKey(peca))
		{
			int qtdeAtual = pecas.get(peca);
			pecas.put(peca, qtdeAtual+1);
		}
		else
		{
			pecas.put(peca, 1);
		}
		
	}

	@Override
	public int getNumberOfParts(boolean distintas) {
		if(distintas) {
			return (int) pecas.keySet().stream().count();
		}
		else {
			IntStream intstream = pecas.values().stream().mapToInt(Number::intValue);
			return intstream.sum();
		}
	}

}
