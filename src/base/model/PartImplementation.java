package base.model;

import java.util.ArrayList;
import java.util.List;

import base.model.interfaces.Part;

/**
 * A implementação da interface Part, usada por todo o programa.
 * @author André Barbosa
 *
 */
public class PartImplementation implements Part {

	private static final long serialVersionUID = 1L;

	private String nome;
	private PartContainer container;
	private String codigo = "";
	private String repositorioOrigem = "";
	private String descricao = "";
	
	public PartImplementation(String nome) {
		container = new PartContainer(nome);
		this.nome = nome;
	}
	
	public PartImplementation(PartImplementation p) {
		this.nome = p.nome;
		this.codigo = p.codigo;
		this.repositorioOrigem = p.repositorioOrigem;
		this.descricao = p.descricao;
		this.container = new PartContainer(p.container);
		
	}

	@Override
	public void setNome(String nome) {
		this.nome = nome;
		
	}
	

	@Override
	public void setCode(String code) {
		this.codigo = code;
	}
	
	@Override
	public String getCode() {
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
		return String.format("%s [cód %s] - %s", nome, codigo, descricao);
	}

	@Override
	public boolean equals(Object b) {
		if(b==null)
			return false;
		if(!(b instanceof PartImplementation)) {
			return false;
		}
		
		PartImplementation pB = (PartImplementation) b;
		if(!(this.codigo.equals(pB.codigo))) {return false;}
		if(!(this.nome.equals(pB.nome))) { return false; }
		if(!(this.descricao.equals(pB.descricao))) { return false;}
		if(!(this.repositorioOrigem.equals(pB.repositorioOrigem))) {return false;}
		if(!(this.container.equals(pB.container))) { return false; }
		return true;
	}
	
	
	public int hashCode() {
		int result = 17;
		result = 31 * result + codigo.hashCode();
		result = 31 * result + nome.hashCode();
		result = 31 * result + descricao.hashCode();
		result = 31 * result + repositorioOrigem.hashCode();
		result = 31 * result + container.hashCode();
		return 0;
	}

}
