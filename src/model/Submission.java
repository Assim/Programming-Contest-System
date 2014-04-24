package model;

import java.io.Serializable;

/**
 * This class will be used to hold a submission which contains references to
 * other classes.
 */
public class Submission implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constant used to denote that a submission is invalidated.
	 */
	public static final int RESULT_PENDING = -2;

	/**
	 * Constant used to denote that a submission is accepted.
	 */
	public static final int RESULT_ACCEPTED = 0;

	/**
	 * Constant used to denote that a submission is not accepted.
	 */
	public static final int RESULT_REJECTED = 1;

	/**
	 * Constant used to denote that the submission did not generate any output.
	 */
	public static final int RESULT_NO_OUTPUT = 2;

	/**
	 * Constant used to denote that there was a compilation error with the
	 * submission.
	 */
	public static final int RESULT_COMPILATION_ERROR = 3;

	/**
	 * Constant used to denote that there was a runtime error with the
	 * submission
	 */
	public static final int RESULT_RUNTIME_ERROR = 4;

	/**
	 * The ID on the database table.
	 */
	private int id;

	/**
	 * The problem for the submission
	 */
	private Problem problem;

	/**
	 * The programming language for the submission
	 */
	private Language language;

	/**
	 * The contestant who submitted the submission
	 */
	private Contestant contestant;

	/**
	 * The source code that was submitted.
	 */
	private String sourceCode;

	/**
	 * The output that was generated
	 */
	private String output;

	/**
	 * The time it was submitted
	 */
	private long timeSubmitted;

	/**
	 * The result of the submission
	 */
	private int result;

	/**
	 * Constructor
	 */
	public Submission() {

	}

	/**
	 * Paramterized constructor
	 */
	public Submission(int id, Problem problem, Language language,
			Contestant contestant, String sourceCode, String output,
			long timeSubmitted, int result) {
		this.id = id;
		this.problem = problem;
		this.language = language;
		this.contestant = contestant;
		this.sourceCode = sourceCode;
		this.output = output;
		this.timeSubmitted = timeSubmitted;
		this.result = result;
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
	 * Set submission problem.
	 */
	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	/**
	 * Returns submission problem.
	 */
	public Problem getProblem() {
		return this.problem;
	}

	/**
	 * Set submission programming language.
	 */
	public void setLanguage(Language language) {
		this.language = language;
	}

	/**
	 * Returns submission programming language.
	 */
	public Language getLanguage() {
		return this.language;
	}

	/**
	 * Sets the contestant for the submission.
	 */
	public void setContestant(Contestant contestant) {
		this.contestant = contestant;
	}

	/**
	 * Returns the contestant of the submission.
	 */
	public Contestant getContestant() {
		return this.contestant;
	}

	/**
	 * Sets the source code that was submitted.
	 */
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	/**
	 * Returns the source code that was submitted.
	 */
	public String getSourceCode() {
		return this.sourceCode;
	}

	/**
	 * Sets the submission output.
	 */
	public void setOutput(String output) {
		this.output = output;
	}

	/**
	 * Returns the submission output.
	 */
	public String getOutput() {
		return this.output;
	}

	/**
	 * Sets the time submitted.
	 */
	public void setTimeSubmitted(long timeSubmitted) {
		this.timeSubmitted = timeSubmitted;
	}

	/**
	 * Returns the submission time.
	 */
	public long getTimeSubmitted() {
		return this.timeSubmitted;
	}

	/**
	 * Set the submission result.
	 */
	public void setResult(int result) {
		this.result = result;
	}

	/**
	 * Returns the submission result.
	 */
	public int getResult() {
		return this.result;
	}

	/**
	 * Get result name by giving the int value
	 */
	public static String getResultName(int result) {
		String value = null;
		switch (result) {
		case -2:
			value = "Pending";
			break;
		case 0:
			value = "Accepted";
			break;
		case 1:
			value = "Rejected";
			break;
		case 2:
			value = "No output";
			break;
		case 3:
			value = "Compilation error";
			break;
		case 4:
			value = "Runtime error";
		}
		return value;
	}

}