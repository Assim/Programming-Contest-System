package util;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JOptionPane;

/**
 * This class contains general tasks that are needed throughout the application.
 */
public final class Utilities {

	/**
	 * Returns the MD5 hash of a string.
	 */
	public static String MD5(String md5) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.substring(1, 3));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
		}
		return null;
	}

	/**
	 * Resets the current application, all it does is delete the PCS folder
	 * which contains all the settings.
	 */
	public static void resetSettings() {
		int n = JOptionPane
				.showConfirmDialog(
						null,
						"Are you sure you would like to reset? This will delete all the configurations and will need to be reconfigured again. The application will close on confirmation.",
						"Reset Configuration", JOptionPane.YES_NO_OPTION);

		// If user clicked on yes
		if (n == JOptionPane.YES_OPTION) {
			File dir = new File("pcs");
			deleteDirectory(dir);
			System.exit(0);
		}
	}

	/**
	 * Force deletion of directory even if it was not empty
	 */
	public static boolean deleteDirectory(File directory) {
		if (directory.exists()) {
			File[] files = directory.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (directory.delete());
	}

	/**
	 * Converts a timestamp to a datetime string.
	 */
	@SuppressWarnings("deprecation")
	public static String getDatetime(long millis) {
		TimeZone.setDefault(TimeZone.getDefault());
		Date date = new Date(millis * 1000);
		return date.getDate() + "/" + (date.getMonth() + 1) + "/"
				+ (date.getYear() + 1900) + " " + date.getHours() + ":"
				+ date.getMinutes();
	}

	/**
	 * Adds a leading zero to the hour or minute string.
	 */
	public static String addLeadingZero(String s) {
		// If it's length is one, then add a zero
		if (s.length() == 1)
			return "0" + s;
		// Otherwise just return it as it is
		return s;
	}

	/**
	 * Converts a String \n to HTML <br>
	 */
	public static String convertToMultiline(String orig) {
		return "<html>" + orig.replaceAll("\n", "<br>");
	}

}