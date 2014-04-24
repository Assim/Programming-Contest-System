package model;

import java.io.Serializable;

import util.Utilities;

/**
 * This class is for holding team information.
 */
public class Contestant implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The ID on the database table.
	 */
	private int id;

	/**
	 * The number of problems solved.
	 */
	private int problemsSolved;

	/**
	 * Total number of penalty for the contestant.
	 */
	private int penalty;

	/**
	 * The contestant username
	 */
	private String username;

	/**
	 * The contestant password
	 */
	private String password;

	/**
	 * Default constructor.
	 */
	public Contestant() {

	}

	/**
	 * Parameterized constructor.
	 */
	public Contestant(int id, int problemsSolved, int penalty, String username,
			String password) {
		this.id = id;
		this.problemsSolved = problemsSolved;
		this.penalty = penalty;
		this.username = username;
		this.password = Utilities.MD5(password);
	}

	/**
	 * Sets the ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the ID
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Sets the number of problems solved for the contestant.
	 */
	public void setProblemsSolved(int problemsSolved) {
		this.problemsSolved = problemsSolved;
	}

	/**
	 * Increments the number of problems solved by 1.
	 */
	public void incrementProblemsSolved() {
		this.problemsSolved++;
	}

	/**
	 * Returns the number of problems solved.
	 */
	public int getProblemsSolved() {
		return this.problemsSolved;
	}

	/**
	 * Sets the penalty for the contestant.
	 */
	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}

	/**
	 * Returns the number of penalty for the contestant.
	 */
	public int getPenalty() {
		return this.penalty;
	}

	/**
	 * Sets the username for the contestant
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Returns the username for the contestant
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Sets the password for the contestant
	 */
	public void setPassword(String password) {
		this.password = Utilities.MD5(password);
	}

	/**
	 * Returns the password for the email
	 */
	public String getPassword() {
		return this.password;
	}

}