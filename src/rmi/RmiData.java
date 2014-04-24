package rmi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import util.DbAdapter;
import model.ContestTime;
import model.Contestant;
import model.Language;
import model.Problem;
import model.Submission;

/**
 * This is used to store data that is being transferred throughout the network
 * between the server and clients. The client will send the username and
 * password periodically, and the server will reply with an object of RmiData
 * containing the latest data.
 */
public class RmiData implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * This is the file where it will be stored. This will be used by the
	 * client.
	 */
	public static final String FILE = "pcs/" + File.separator + "data.ser";

	/**
	 * This is the login result whether it was successful or not.
	 */
	private boolean loginResult;

	/**
	 * This is information about the current contestant, the one who logged in.
	 */
	private Contestant contestant;

	/**
	 * Contains a list of all contestants. They will be arranged depending on
	 * the current standings.
	 */
	private ArrayList<Contestant> contestants;

	/**
	 * This will contain all the languages that can be used for submitting
	 * solutions.
	 */
	private ArrayList<Language> languages;

	/**
	 * This will contain all the problems.
	 */
	private ArrayList<Problem> problems;

	/**
	 * This will contain the list of submissions for the current user who logged
	 * in only.
	 */
	private ArrayList<Submission> submissions;

	/**
	 * This will show how much time is left. It used this way just in case the
	 * server changes/extends the time while the contest is still running.
	 */
	private String timeLeft;

	/**
	 * This will return whether the contest is still running or not. It will be
	 * used to know whether client can submit or not.
	 */
	private boolean contestRunning;

	/**
	 * This no-arg constructor is for client use. This will load from the
	 * serialized file as clients store the latest received object in a file.
	 */
	public RmiData() {
		loadFromFile(); // Load the object from file
	}

	/**
	 * This constructor should be used by the server. The param passed should be
	 * the contestant logging in.
	 */
	public RmiData(String username, String password) {
		this.loginResult = DbAdapter.contestantLogin(username, password); // set
																			// login
																			// result
		if (loginResult) { // if it was true, then set data
			this.contestant = DbAdapter.getContestantByUsername(username); // for
																			// current
																			// contestant
			this.contestants = DbAdapter.getScoreboard();
			this.languages = DbAdapter.getAllLanguages();
			this.problems = DbAdapter.getAllProblems();
			this.submissions = DbAdapter.getAllSubmissions(this.contestant);

			// Get contest time object
			ContestTime ct = new ContestTime();

			this.contestRunning = false; // Set it as default value
			// If value is null, then leave it as default value
			try {
				this.contestRunning = ct.canSubmit();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			this.timeLeft = "00:00"; // Set it as default just in case it was
										// null.
			// If contest time is null, then just catch and use previous value.
			try {
				this.timeLeft = ct.getDurationBreakdown();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sets login result.
	 */
	public void setLoginResult(boolean loginResult) {
		this.loginResult = loginResult;
	}

	/**
	 * Returns login result.
	 */
	public boolean getLoginResult() {
		return this.loginResult;
	}

	/**
	 * Sets the contestant.
	 */
	public void setContestant(Contestant contestant) {
		this.contestant = contestant;
	}

	/**
	 * Returns the contestant.
	 */
	public Contestant getContestant() {
		return this.contestant;
	}

	/**
	 * Sets contestants.
	 */
	public void setContestants(ArrayList<Contestant> contestants) {
		this.contestants = contestants;
	}

	/**
	 * Returns contestant.
	 */
	public ArrayList<Contestant> getContestants() {
		return this.contestants;
	}

	/**
	 * Sets languages.
	 */
	public void setLanguages(ArrayList<Language> languages) {
		this.languages = languages;
	}

	/**
	 * Returns languages.
	 */
	public ArrayList<Language> getLanguages() {
		return this.languages;
	}

	/**
	 * Sets problems.
	 */
	public void setProblems(ArrayList<Problem> problems) {
		this.problems = problems;
	}

	/**
	 * Returns problems.
	 */
	public ArrayList<Problem> getProblems() {
		return this.problems;
	}

	/**
	 * Sets submissions.
	 */
	public void setSubmissions(ArrayList<Submission> submissions) {
		this.submissions = submissions;
	}

	/**
	 * Returns submissions.
	 */
	public ArrayList<Submission> getSubmissions() {
		return this.submissions;
	}

	/**
	 * Sets time left.
	 */
	public void setTimeLeft(String timeLeft) {
		this.timeLeft = timeLeft;
	}

	/**
	 * Returns time left.
	 */
	public String getTimeLeft() {
		return this.timeLeft;
	}

	/**
	 * Set whether the contest is running or not.
	 */
	public void setContestRunning(boolean contestRunning) {
		this.contestRunning = contestRunning;
	}

	/**
	 * Returns whether the contest is running or not.
	 */
	public boolean getContestRunning() {
		return this.contestRunning;
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
		if (!fileExists())
			return; // Exit if file doesn't exist
		RmiData o = null;
		try {
			FileInputStream fileIn = new FileInputStream(FILE);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			o = (RmiData) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		this.setLoginResult(o.getLoginResult());
		this.setContestants(o.getContestants());
		this.setLanguages(o.getLanguages());
		this.setProblems(o.getProblems());
		this.setSubmissions(o.getSubmissions());
		this.setTimeLeft(o.getTimeLeft());
		this.setContestRunning(o.getContestRunning());
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