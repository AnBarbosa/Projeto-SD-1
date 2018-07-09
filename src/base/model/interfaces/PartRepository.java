package base.model.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface PartRepository extends Remote {
	
	public String getName() throws RemoteException;
	public String addPart(Part peca) throws RemoteException;
	public long getNumberOfParts(boolean distintas) throws RemoteException;
	public Map<Part, Integer> getComponentsMap() throws RemoteException;
	public Part getPart(String code) throws RemoteException;
	
	
}
