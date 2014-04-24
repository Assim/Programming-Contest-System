package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import util.DbAdapter;
import model.ContestTime;
import model.Submission;

/**
 * This is the RMI implementation class which is run by the server.
 */
public class RmiImpl extends UnicastRemoteObject implements Rmi {

	private static final long serialVersionUID = 1L;

	/**
	 * Empty constructor throwing a RemoteException
	 */
	public RmiImpl() throws RemoteException {
	}

	/**
	 * The client sends the contestants data, and the server will reply with all
	 * the data as a SocketData object.
	 */
	@Override
	public RmiData getData(String username, String password)
			throws RemoteException {
		// Create new object of socket data and pass contestant
		// Contestant has to pass username and password each time in the
		// Contestant object
		return new RmiData(username, password);
	}

	/**
	 * This is where the client sends a submission as a submission object. This
	 * will add the submission to the server database which will be later dealt
	 * with the CodeRunner to actually validate the code.
	 */
	@Override
	public boolean sendSubmission(Submission submission, String username)
			throws RemoteException {
		ContestTime ct = new ContestTime();
		if (ct.canSubmit()) {
			submission.setResult(Submission.RESULT_PENDING); // Set is as
																// pending
			submission.setTimeSubmitted(System.currentTimeMillis() / 1000); // Set
																			// submitted
																			// now
			submission.setOutput(""); // empty output
			submission.setContestant(DbAdapter
					.getContestantByUsername(username));
			DbAdapter.addSubmission(submission);
			return true;
		} else {
			return false;
		}
	}

}