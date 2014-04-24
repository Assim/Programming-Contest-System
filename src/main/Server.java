package main;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.Submission;
import rmi.RmiImpl;
import rmi.Rmi;
import util.CodeRunner;
import util.DbAdapter;
import view.server.ServerView;

/**
 * The job of this class is to start up the server views and tasks.
 */
public class Server {

	private ServerView serverView;
	private ServerUpdater serverUpdater;
	private CodeRunnerThread codeRunnerThread;

	/**
	 * The constructor for the server controller. It starts an RMI service and
	 * spawns thread that updates the server and looks for code to compile and
	 * execute.
	 */
	public Server() {
		// Show server view
		serverView = new ServerView(this);
		serverView.getFrame().setVisible(true);

		// Start server and listen from port 1099
		Rmi rmiService = null;
		try {
			rmiService = new RmiImpl();
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind("pcs", rmiService); // Object will be used as a
												// service by clients
		} catch (RemoteException e) {
			// Problem with starting the server
			JOptionPane.showMessageDialog(null,
					"Server can't start service. Please restart server.");
			e.printStackTrace();
		}

		// Start server updater thread
		serverUpdater = new ServerUpdater();
		serverUpdater.start();

		// Start code runner thread
		codeRunnerThread = new CodeRunnerThread();
		codeRunnerThread.start();
	}

	/**
	 * The job of this thread is to periodically update the ServerView if
	 * there's new data.
	 */
	class ServerUpdater extends Thread {

		public void run() {
			while (true) {
				// Update all the views in the server
				serverView.updateViews();

				// Sleep for a minute
				try {
					Thread.sleep(30000); // Update every 30 seconds.
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * The job of this thread is to check for pending submissions and compile
	 * them.
	 */
	class CodeRunnerThread extends Thread {

		public void run() {
			while (true) {
				ArrayList<Submission> submissions = DbAdapter
						.getPendingSubmissions();

				// If no pending submissions
				if (submissions.size() == 0) {
					try {
						// Then rest for half a minute
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					// Run and validate the first submission available and
					// update DB.
					DbAdapter.updateSubmission(CodeRunner
							.runAndValidate(submissions.get(0)));
				}
			}
		}
	}

}