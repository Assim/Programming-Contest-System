package main;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;

import model.Submission;
import rmi.Rmi;
import rmi.RmiData;
import util.ConfigManager;
import view.client.ClientLoginView;
import view.client.ClientView;

/**
 * The job of this class is to start up the client views and tasks.
 */
public class Client {

	private ClientLoginView loginView;
	private ClientView clientView;
	private ClientUpdater clientUpdater;
	private String username;
	private String password;

	/**
	 * Constructor.
	 */
	public Client() {
		loginView = new ClientLoginView(this);
		loginView.getFrame().setVisible(true);
	}

	/**
	 * Run when client tries to login. If info correct, show client and start
	 * appropriate threads.
	 */
	public void login(String username, String password) {
		// Get instance of ConfigManager from file
		ConfigManager cm = new ConfigManager();

		try {
			// Try to connect to RMI through port 1099
			Registry registry = LocateRegistry.getRegistry(cm.getServerIp(),
					1099);
			Rmi rmi = (Rmi) registry.lookup("pcs");

			// Send login data
			RmiData socketData = rmi.getData(username, password);

			// If login details are correct
			if (socketData.getLoginResult()) {
				// Store username and password for future updates
				this.username = username;
				this.password = password;
				socketData.saveToFile(); // save Rmi data to file

				// Show client view frame and close the login view
				clientView = new ClientView(this, username);
				clientView.getFrame().setVisible(true);
				loginView.getFrame().dispose();

				// Start updater thread
				clientUpdater = new ClientUpdater();
				clientUpdater.start();
			} else {
				// Login failed
				JOptionPane.showMessageDialog(null,
						"Login failed. Make sure of username and password.");
			}
		} catch (RemoteException | NotBoundException e) {
			// Problem with the server connection
			JOptionPane.showMessageDialog(null, "Could not connect to server.");
			e.printStackTrace();
		}
	}

	/**
	 * This code runs when client tries to submit a submission to the server.
	 */
	public boolean submit(Submission submission) {
		boolean result = false;

		// Get ConfigManager object
		ConfigManager cm = new ConfigManager();

		try {
			// Try to connect to RMI through port 1099
			Registry registry = LocateRegistry.getRegistry(cm.getServerIp(),
					1099);
			Rmi rmi = (Rmi) registry.lookup("pcs");

			// Send submission
			if (rmi.sendSubmission(submission, this.username)) {
				// Received by server
				JOptionPane.showMessageDialog(null,
						"Submission sent. Validation will take place soon.");
				result = true;
			} else {
				// Not accepted (contest not running)
				JOptionPane.showMessageDialog(null,
						"Submissions closed. Contest isn't running.");
				result = false;
			}

		} catch (RemoteException | NotBoundException e) {
			// Problem with the server connection
			JOptionPane.showMessageDialog(null, "Could not connect to server.");
			result = false;
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * This thread will fetch new data from the server periodically.
	 */
	class ClientUpdater extends Thread {

		public void run() {
			// Get config data
			ConfigManager cm = new ConfigManager();
			// Connect to server through RMI port 1099.
			Rmi rmi = null;
			try {
				Registry registry = LocateRegistry.getRegistry(
						cm.getServerIp(), 1099);
				rmi = (Rmi) registry.lookup("pcs");
			} catch (RemoteException | NotBoundException e) {
				JOptionPane.showMessageDialog(null,
						"Cannot connect to server. Please restart system.");
				e.printStackTrace();
				System.exit(0);
			}

			// Keep fetching new data every half a minute from the server.
			while (true) {
				try {
					RmiData socketData = rmi.getData(username, password);
					socketData.saveToFile();
					clientView.updateViews(); // it will automatically take the
												// data from the saved file
					Thread.sleep(30000); // check every 30 seconds
				} catch (InterruptedException | RemoteException e) {
					// If there was an error getting data from server
					JOptionPane
							.showMessageDialog(null,
									"Lost connection with server. Please restart system.");
					e.printStackTrace();
					System.exit(0);
				}
			}
		}
	}
}