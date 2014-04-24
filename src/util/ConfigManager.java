package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * This is going to manage the configuration file by setting and getting data
 * from the configuration file and will also save and load the file
 * appropriately.
 */
public class ConfigManager implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * This constant is used to denote that the role of the computer is server.
	 */
	public static final int ROLE_SERVER = 1;

	/**
	 * This constant is used to denote that the role of the computer is client.
	 */
	public static final int ROLE_CLIENT = 2;

	/**
	 * The serialized configuration file.
	 */
	public static final String CONFIG_FILE = "pcs" + File.separator
			+ "pcs.conf";

	/**
	 * The role of the server. This will be set by one of the constant values.
	 */
	private int role;

	/**
	 * The server IP address in which the user will connect to.
	 */
	private String serverIp;

	/**
	 * When a new object is created, it will directly load the configuration
	 * file if it exists
	 */
	public ConfigManager() {
		loadFromFile();
	}

	/**
	 * Sets the role.
	 */
	public boolean setRole(int role) {
		if (role != ROLE_SERVER || role != ROLE_CLIENT) {
			this.role = role;
			saveToFile();
			return true;
		}
		return false;
	}

	/**
	 * Returns the role.
	 */
	public int getRole() {
		return this.role;
	}

	/**
	 * Sets the server IP address.
	 */
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
		saveToFile();
	}

	/**
	 * Returns the server IP address.
	 */
	public String getServerIp() {
		return this.serverIp;
	}

	/**
	 * Serializes the current object into the configuration file.
	 */
	private void saveToFile() {
		try {
			FileOutputStream fileOut = new FileOutputStream(CONFIG_FILE);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deserializes the configuration file and loads into the current object.
	 */
	private void loadFromFile() {
		if (!configExists())
			return; // Exit if file doesn't exist
		ConfigManager o = null;
		try {
			FileInputStream fileIn = new FileInputStream(CONFIG_FILE);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			o = (ConfigManager) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		this.setRole(o.getRole());
		this.setServerIp(o.getServerIp());
	}

	/**
	 * Deletes the configuration file.
	 */
	public static void deleteConfig() {
		File file = new File(CONFIG_FILE);
		file.delete();
	}

	/**
	 * Check if configuration file exists
	 */
	public static boolean configExists() {
		File file = new File(CONFIG_FILE);
		if (file.exists())
			return true;
		else
			return false;
	}

}