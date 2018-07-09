package base.model;

import java.util.ArrayList;
import java.util.List;

public class PartImplementation implements Part {

	private static final long serialVersionUID = 1L;

	private PartContainer container;
	private long codigo;
	private String nome;
	private String repositorioOrigem;

	private String descricao;
	
	public PartImplementation(String nome) {
		container = new PartContainer(nome);
		this.nome = nome;
	}
	
	public PartImplementation(PartImplementation p) {
		this.nome = p.nome;
		this.codigo = p.codigo;
		this.repositorioOrigem = p.repositorioOrigem;
		this.descricao = p.descricao;
		this.container = new PartContainer(container);
		
	}

	@Override
	public void setNome(String nome) {
		this.nome = nome;
		
	}
	

	@Override
	public void setCode(long code) {
		this.codigo = code;
	}
	
	@Override
	public long getCode() {
		return codigo;
	}

	@Override
	public String getNome() {
		return nome;
	}

	@Override
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	@Override
	public String getDescricao() {
		return descricao;
	}

	@Override
	public void setRepositorioDeOrigem(String repo) {
		this.repositorioOrigem = repo;
	}
	@Override
	public String getRepositorioDeOrigem() {
		return repositorioOrigem;
	}

	@Override
	public long getNumberOfParts(boolean distintas) {
		return container.getNumberOfParts(distintas);
	}

	@Override
	public List<Componente> listComponents() {
		List<Componente> comps = new ArrayList<Componente>();
		 
		container.getComponentsMap().forEach((comp, valor)->{
			Componente c = new Componente(comp, valor);
			comps.add(c);
		});
		
		return comps;

	}
	
	@Override
	public Object clone() {
		PartImplementation pClone = new PartImplementation(this);
		return pClone;
	}

	@Override
	public void addComponent(Part parte) {
		container.addPart(parte);
	}

	@Override
	public void clearComponents() {
		container.clear();
		
	}
	
	public String toString() {
		return String.format("%s [cód %d] - %s", nome, codigo, descricao);
	}

	
}
