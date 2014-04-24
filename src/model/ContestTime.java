package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import util.Utilities;

/**
 * ContestTime will be serialized to a file in the server. As for the client, it
 * will an instance variable of SocketData.
 */
public class ContestTime implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String FILE = "pcs" + File.separator + "time.ser";

	private long timeStart;
	private long timeEnd;

	/**
	 * The constructor will automatically load the object from file.
	 */
	public ContestTime() {
		loadFromFile();
	}

	/**
	 * Set the start time.
	 */
	public void setTimeStart(long timeStart) {
		this.timeStart = timeStart;
		saveToFile();
	}

	/**
	 * Returns the start time.
	 */
	public long getTimeStart() {
		return this.timeStart;
	}

	/**
	 * Set the end time.
	 */
	public void setTimeEnd(long timeEnd) {
		this.timeEnd = timeEnd;
		saveToFile();
	}

	/**
	 * Returns the end time.
	 */
	public long getTimeEnd() {
		return this.timeEnd;
	}

	/**
	 * Check if the user can submit or the competition isn't running anymore.
	 */
	public boolean canSubmit() {
		if (System.currentTimeMillis() > timeStart
				&& System.currentTimeMillis() < timeEnd)
			return true;
		else
			return false;
	}

	/**
	 * Get contest duration breakdown.
	 */
	public String getDurationBreakdown() {
		long millis = timeEnd - System.currentTimeMillis();

		// If can't submit, then contest isn't running
		if (!canSubmit()) {
			return "00:00";
		}

		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		millis -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
		millis -= TimeUnit.MINUTES.toMillis(minutes);

		StringBuilder sb = new StringBuilder(64);
		sb.append(Utilities.addLeadingZero(String.valueOf(hours)));
		sb.append(":");
		sb.append(Utilities.addLeadingZero(String.valueOf(minutes)));

		return (sb.toString());
	}

	/**
	 * Serializes the current object into the file.
	 */
	public void saveToFile() {
		try {
			FileOutputStream fileOut = new FileOutputStream(FILE);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deserializes the file and loads into the current object.
	 */
	public void loadFromFile() {
		File file = new File(FILE);
		if (!file.exists())
			return; // Exit if file doesn't exist
		ContestTime o = null;
		try {
			FileInputStream fileIn = new FileInputStream(FILE);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			o = (ContestTime) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		this.setTimeStart(o.getTimeStart());
		this.setTimeEnd(o.getTimeEnd());
	}

	/**
	 * Deletes the file.
	 */
	public static void deleteFile() {
		File file = new File(FILE);
		file.delete();
	}

	/**
	 * Check if file exists
	 */
	public static boolean fileExists() {
		File file = new File(FILE);
		if (file.exists())
			return true;
		else
			return false;
	}

}