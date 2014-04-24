package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import model.Submission;

/**
 * This class is the RMI interface, this will be used by the client when
 * invoking the methods, and it will be used by the server as it implements
 * this.
 */
public interface Rmi extends Remote {

	public RmiData getData(String username, String password)
			throws RemoteException;

	public boolean sendSubmission(Submission submission, String username)
			throws RemoteException;

}