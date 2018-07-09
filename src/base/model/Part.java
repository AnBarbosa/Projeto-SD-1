package base.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface Part extends Serializable, Cloneable{

	void setCode(long code);
	public long getCode();
	
	public void setNome(String nome);
	public String getNome();
	
	public void setDescricao(String descricao);
	public String getDescricao();
	
	public void setRepositorioDeOrigem(String repo);
	public String getRepositorioDeOrigem();

	public long getNumberOfParts(boolean distintas);
	public List<Componente> listComponents();
	
	public void addComponent(Part grabbed);
	
	public void clearComponents();
	
	public Object clone();
	
	public class Componente{
		private Part parte;
		private long quantidade;
		public Componente(Part p, long q) {parte = p; quantidade = q;}
		public Part getPart() {
			return (Part) parte.clone();
		}
		public long getQuantidade() {return quantidade;}
		public String toString() {
			return String.format("%s(%d)", parte.getNome(), quantidade);
		}
	}

	


	
}
