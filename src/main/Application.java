package main;

import util.ConfigManager;
import view.MainView;

/**
 * This is the main class of the whole application.
 */
public class Application {

	/**
	 * This is the starting point of the application.
	 */
	public static void main(String[] args) {
		// Check if this PC is already configured
		if (ConfigManager.configExists()) {
			// Get instance of ConfigManager which will read from file
			ConfigManager cm = new ConfigManager();

			// Run correct class based on current role.
			if (cm.getRole() == ConfigManager.ROLE_CLIENT)
				new Client();
			else if (cm.getRole() == ConfigManager.ROLE_SERVER)
				new Server();
		} else {
			// If not configured, show main view.
			new MainView().show();
		}
	}

}