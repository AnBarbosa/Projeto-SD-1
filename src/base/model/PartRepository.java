package base.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PartRepository extends Remote {
	
	public String getName() throws RemoteException;
	public void addPart(Part peca) throws RemoteException;
	public int getNumberOfParts(boolean distintas) throws RemoteException;
}
