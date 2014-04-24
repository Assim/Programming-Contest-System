package model;

import java.io.Serializable;

/**
 * This class will be used to hold information about a certain programming
 * language.
 */
public class Language implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The ID on the database table.
	 */
	private int id;

	/**
	 * Name of the programming language.
	 */
	private String name;

	/**
	 * File extension name
	 */
	private String fileExtension;

	/**
	 * The command line compile command for the programming language.
	 */
	private String cmdCompile;

	/**
	 * The command line execute command for the programming language.
	 */
	private String cmdExecute;

	/**
	 * The constructor.
	 */
	public Language() {

	}

	/**
	 * The parameterized constructor.
	 */
	public Language(int id, String name, String fileExtension,
			String cmdCompile, String cmdExecute) {
		this.id = id;
		this.name = name;
		this.fileExtension = fileExtension;
		this.cmdCompile = cmdCompile;
		this.cmdExecute = cmdExecute;
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
	 * Sets the programming language name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the programming language name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Set file extension name
	 */
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	/**
	 * Returns file extension name
	 */
	public String getFileExtension() {
		return this.fileExtension;
	}

	/**
	 * Set the command line compile.
	 */
	public void setCmdCompile(String cmdCompile) {
		this.cmdCompile = cmdCompile;
	}

	/**
	 * Returns the command line compile.
	 */
	public String getCmdCompile() {
		return this.cmdCompile;
	}

	/**
	 * Set the command line execute.
	 */
	public void setCmdExecute(String cmdExecute) {
		this.cmdExecute = cmdExecute;
	}

	/**
	 * Returns the command line execute.
	 */
	public String getCmdExecute() {
		return this.cmdExecute;
	}

}