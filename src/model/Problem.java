package model;

import java.io.Serializable;

/**
 * This class will be used to hold information about problems (questions).
 */
public class Problem implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The ID of the problem.
	 */
	private int id;

	/**
	 * The problem name.
	 */
	private String problemName;

	/**
	 * The file name for the submission.
	 */
	private String fileName;

	/**
	 * The input that will be used to test the submission.
	 */
	private String input;

	/**
	 * The output that the submission should generate in the output file.
	 */
	private String output;

	/**
	 * The constructor.
	 */
	public Problem() {

	}

	/**
	 * The parameterized constructor.
	 */
	public Problem(int id, String problemName, String fileName, String input,
			String output) {
		this.id = id;
		this.problemName = problemName;
		this.fileName = fileName;
		this.input = input;
		this.output = output;
	}

	/**
	 * Set the problem ID.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the problem ID.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Set the problem name.
	 */
	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}

	/**
	 * Get the problem name.
	 */
	public String getProblemName() {
		return this.problemName;
	}

	/**
	 * Set the problem file name. Such as FileName.(cpp|java).
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Get the problem file name.
	 */
	public String getFilename() {
		return this.fileName;
	}

	/**
	 * Set the input text.
	 */
	public void setInput(String input) {
		this.input = input;
	}

	/**
	 * Get the input text.
	 */
	public String getInput() {
		return this.input;
	}

	/**
	 * Set the expected output.
	 */
	public void setOutput(String output) {
		this.output = output;
	}

	/**
	 * Get the expected output.
	 */
	public String getOutput() {
		return this.output;
	}

}