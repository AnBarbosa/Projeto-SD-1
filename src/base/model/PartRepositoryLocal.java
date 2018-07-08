package base.model;

public interface PartRepositoryLocal{
	
	public String lgetName();
	public void laddPart(Part peca);
	public int lgetNumberOfParts(boolean distintas);
}
