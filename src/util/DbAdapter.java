package util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import model.Contestant;
import model.Language;
import model.Problem;
import model.Submission;

/**
 * This class is used to communicate with the SQLite database, and it's methods
 * performs various CRUD tasks.
 */
public final class DbAdapter {

	/**
	 * This is connection string that will be used to reference the database
	 * file.
	 */
	private static final String CONNECTION_STRING = "jdbc:sqlite:pcs"
			+ File.separator + "pcs.db";

	/**
	 * This will be used to create and set up the database and tables if they
	 * don't exist.
	 */
	public static synchronized void createTables() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);

			stmt = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS CONTESTANT "
					+ "(ID INTEGER PRIMARY KEY     AUTOINCREMENT,"
					+ "PROBLEMS_SOLVED INT    NOT NULL,"
					+ "PENALTY         INT     NOT NULL,"
					+ "USERNAME        TEXT    NOT NULL,"
					+ "PASSWORD        TEXT    NOT NULL)";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE IF NOT EXISTS LANGUAGE "
					+ "(ID INTEGER PRIMARY KEY     AUTOINCREMENT,"
					+ "NAME            TEXT    NOT NULL,"
					+ "FILE_EXTENSION  TEXT    NOT NULL,"
					+ "CMD_COMPILE     TEXT    NOT NULL,"
					+ "CMD_EXECUTE     TEXT    NOT NULL)";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE IF NOT EXISTS PROBLEM "
					+ "(ID INTEGER PRIMARY KEY     AUTOINCREMENT,"
					+ "TITLE           TEXT    NOT NULL,"
					+ "FILE_NAME       TEXT    NOT NULL,"
					+ "INPUT           TEXT    NOT NULL,"
					+ "OUTPUT          TEXT    NOT NULL)";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE IF NOT EXISTS SUBMISSION "
					+ "(ID INTEGER PRIMARY KEY     AUTOINCREMENT,"
					+ "PROBLEM_ID      INT     NOT NULL,"
					+ "LANGUAGE_ID     INT     NOT NULL,"
					+ "CONTESTANT_ID   INT     NOT NULL,"
					+ "SOURCE_CODE     TEXT    NOT NULL,"
					+ "OUTPUT          TEXT    NOT NULL,"
					+ "TIME            INT     NOT NULL,"
					+ "RESULT          INT     NOT NULL)";
			stmt.executeUpdate(sql);

			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * This will be used to delete the database.
	 */
	public static synchronized void deleteDb() {
		File file = new File("pcs" + File.separator + "pcs.db");
		file.delete();
	}

	/**
	 * This will add a contestant to the database by taking a contestant object.
	 */
	public static synchronized void addContestant(Contestant o) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = "INSERT INTO CONTESTANT (PROBLEMS_SOLVED,PENALTY,USERNAME,PASSWORD) "
					+ "VALUES ("
					+ o.getProblemsSolved()
					+ ", "
					+ o.getPenalty()
					+ ", '"
					+ o.getUsername()
					+ "', '"
					+ o.getPassword() + "' );";
			stmt.executeUpdate(sql);

			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * This will return a Contestant object for a certain contestant based on
	 * the ID.
	 */
	public static synchronized Contestant getContestant(int id) {
		Connection c = null;
		Statement stmt = null;
		Contestant contestant = null; // Result storage
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM CONTESTANT WHERE ID = '" + id
							+ "';");
			rs.next();
			contestant = new Contestant(rs.getInt("ID"),
					rs.getInt("PROBLEMS_SOLVED"), rs.getInt("PENALTY"),
					rs.getString("USERNAME"), rs.getString("PASSWORD"));
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return contestant;
	}

	/**
	 * This will return a Contestant object for a certain contestant by
	 * username.
	 */
	public static synchronized Contestant getContestantByUsername(
			String username) {
		Connection c = null;
		Statement stmt = null;
		Contestant contestant = null; // Result storage
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM CONTESTANT WHERE USERNAME = '"
							+ username + "';");
			rs.next();
			contestant = new Contestant(rs.getInt("ID"),
					rs.getInt("PROBLEMS_SOLVED"), rs.getInt("PENALTY"),
					rs.getString("USERNAME"), rs.getString("PASSWORD"));
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return contestant;
	}

	/**
	 * This will return an ArrayList of all the contestants.
	 */
	public static synchronized ArrayList<Contestant> getAllContestants() {
		Connection c = null;
		Statement stmt = null;
		ArrayList<Contestant> contestants = new ArrayList<Contestant>();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM CONTESTANT;");
			while (rs.next()) {
				contestants.add(new Contestant(rs.getInt("ID"), rs
						.getInt("PROBLEMS_SOLVED"), rs.getInt("PENALTY"), rs
						.getString("USERNAME"), rs.getString("PASSWORD")));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return contestants;
	}

	/**
	 * This will take a Contestant and update it on the database. It takes a
	 * second parameter called ignorePass and it is used to specify whether to
	 * update the password of the contestant or not.
	 */
	public static synchronized void updateContestant(Contestant o,
			boolean ignorePass) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = "";
			if (ignorePass) {
				sql = "UPDATE CONTESTANT set PROBLEMS_SOLVED = "
						+ o.getProblemsSolved() + ", PENALTY = "
						+ o.getPenalty() + ", USERNAME = '" + o.getUsername()
						+ "' where ID=" + o.getId() + ";";
			} else {
				sql = "UPDATE CONTESTANT set PROBLEMS_SOLVED = "
						+ o.getProblemsSolved() + ", PENALTY = "
						+ o.getPenalty() + ", USERNAME = '" + o.getUsername()
						+ "', PASSWORD = '" + o.getPassword() + "' where ID="
						+ o.getId() + ";";
			}

			stmt.executeUpdate(sql);
			c.commit();

			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * This will delete a certain contestant based on the ID.
	 */
	public static synchronized void deleteContestant(int id) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = "DELETE from CONTESTANT where ID=" + id + ";";
			stmt.executeUpdate(sql);
			c.commit();

			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * This will verify a login based on a username and password (MD5-hashed).
	 */
	public static synchronized boolean contestantLogin(String username,
			String password) {
		Connection c = null;
		Statement stmt = null;
		boolean result = false;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = "SELECT * FROM CONTESTANT WHERE USERNAME='" + username
					+ "' AND PASSWORD = '" + password + "';";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next())
				result = true; // If user exists, result is true.
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return result;
	}

	/**
	 * This will return an ArrayList of all contestants but will order them
	 * based on the most problems solved and less penalties.
	 */
	public static synchronized ArrayList<Contestant> getScoreboard() {
		Connection c = null;
		Statement stmt = null;
		ArrayList<Contestant> contestants = new ArrayList<Contestant>();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM CONTESTANT ORDER BY PROBLEMS_SOLVED DESC, PENALTY ASC;");
			while (rs.next()) {
				contestants.add(new Contestant(rs.getInt("ID"), rs
						.getInt("PROBLEMS_SOLVED"), rs.getInt("PENALTY"), rs
						.getString("USERNAME"), rs.getString("PASSWORD")));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return contestants;
	}

	/**
	 * This will be used to add a language by taking a Language object.
	 */
	public static synchronized void addLanguage(Language o) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = "INSERT INTO LANGUAGE (NAME,FILE_EXTENSION,CMD_COMPILE,CMD_EXECUTE) "
					+ "VALUES ('"
					+ o.getName()
					+ "', '"
					+ o.getFileExtension()
					+ "', '"
					+ o.getCmdCompile()
					+ "', '"
					+ o.getCmdExecute()
					+ "' );";
			stmt.executeUpdate(sql);

			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * This will return a language object based on the Language ID.
	 */
	public static synchronized Language getLanguage(int id) {
		Connection c = null;
		Statement stmt = null;
		Language language = null; // Result storage
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM LANGUAGE WHERE ID = '" + id
							+ "';");
			rs.next();
			language = new Language(rs.getInt("ID"), rs.getString("NAME"),
					rs.getString("FILE_EXTENSION"),
					rs.getString("CMD_COMPILE"), rs.getString("CMD_EXECUTE"));
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return language;
	}

	/**
	 * This will return an ArrayList of all the languages in the database.
	 */
	public static synchronized ArrayList<Language> getAllLanguages() {
		Connection c = null;
		Statement stmt = null;
		ArrayList<Language> languages = new ArrayList<Language>();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM LANGUAGE;");
			while (rs.next()) {
				languages
						.add(new Language(rs.getInt("ID"),
								rs.getString("NAME"), rs
										.getString("FILE_EXTENSION"), rs
										.getString("CMD_COMPILE"), rs
										.getString("CMD_EXECUTE")));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return languages;
	}

	/**
	 * This will update a certain language by taking a language object.
	 */
	public static synchronized void updateLanguage(Language o) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = "UPDATE LANGUAGE set NAME = '" + o.getName()
					+ "', FILE_EXTENSION = '" + o.getFileExtension()
					+ "', CMD_COMPILE = '" + o.getCmdCompile()
					+ "', CMD_EXECUTE = '" + o.getCmdExecute() + "' where ID="
					+ o.getId() + ";";
			stmt.executeUpdate(sql);
			c.commit();

			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * This will delete a certain language from the database based on the ID.
	 */
	public static synchronized void deleteLanguage(int id) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = "DELETE from LANGUAGE where ID=" + id + ";";
			stmt.executeUpdate(sql);
			c.commit();

			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * Adds a problem to the database by taking a Problem object.
	 */
	public static synchronized void addProblem(Problem o) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = "INSERT INTO PROBLEM (TITLE,FILE_NAME,INPUT,OUTPUT) "
					+ "VALUES ('" + o.getProblemName() + "', '"
					+ o.getFilename() + "', '" + o.getInput() + "', '"
					+ o.getOutput() + "' );";
			stmt.executeUpdate(sql);

			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * Returns a Problem object based on the ID.
	 */
	public static synchronized Problem getProblem(int id) {
		Connection c = null;
		Statement stmt = null;
		Problem problem = null; // Result storage
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM PROBLEM WHERE ID = '" + id
							+ "';");
			rs.next();
			problem = new Problem(rs.getInt("ID"), rs.getString("TITLE"),
					rs.getString("FILE_NAME"), rs.getString("INPUT"),
					rs.getString("OUTPUT"));
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return problem;
	}

	/**
	 * Returns an ArrayList of all the problems in the database.
	 */
	public static synchronized ArrayList<Problem> getAllProblems() {
		Connection c = null;
		Statement stmt = null;
		ArrayList<Problem> problems = new ArrayList<Problem>();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PROBLEM;");
			while (rs.next()) {
				problems.add(new Problem(rs.getInt("ID"),
						rs.getString("TITLE"), rs.getString("FILE_NAME"), rs
								.getString("INPUT"), rs.getString("OUTPUT")));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return problems;
	}

	/**
	 * Updates a problem by taking a Problem object.
	 */
	public static synchronized void updateProblem(Problem o) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = "UPDATE PROBLEM set TITLE = '" + o.getProblemName()
					+ "', FILE_NAME = '" + o.getFilename() + "', INPUT = '"
					+ o.getInput() + "', OUTPUT = '" + o.getOutput()
					+ "' where ID=" + o.getId() + ";";
			stmt.executeUpdate(sql);
			c.commit();

			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * Deletes a problem based on the ID.
	 */
	public static synchronized void deleteProblem(int id) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = "DELETE from PROBLEM where ID=" + id + ";";
			stmt.executeUpdate(sql);
			c.commit();

			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * Adds a submission by taking a Submission object.
	 */
	public static synchronized void addSubmission(Submission o) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = "INSERT INTO SUBMISSION (PROBLEM_ID,LANGUAGE_ID,CONTESTANT_ID,SOURCE_CODE,OUTPUT,TIME,RESULT) "
					+ "VALUES ("
					+ o.getProblem().getId()
					+ ", "
					+ o.getLanguage().getId()
					+ ", "
					+ o.getContestant().getId()
					+ ", '"
					+ o.getSourceCode()
					+ "', '"
					+ o.getOutput()
					+ "', "
					+ o.getTimeSubmitted()
					+ ", " + o.getResult() + " );";
			stmt.executeUpdate(sql);

			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * Returns a Submission object based on the ID.
	 */
	public static synchronized Submission getSubmission(int id) {
		Connection c = null;
		Statement stmt = null;
		Submission submission = null; // Result storage
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM SUBMISSION WHERE ID = '" + id
							+ "';");
			rs.next();

			// Get problem
			stmt = c.createStatement();
			ResultSet rsProblem = stmt
					.executeQuery("SELECT * FROM PROBLEM WHERE ID = '"
							+ rs.getInt("PROBLEM_ID") + "';");
			rsProblem.next();
			Problem problem = new Problem(rsProblem.getInt("ID"),
					rsProblem.getString("TITLE"),
					rsProblem.getString("FILE_NAME"),
					rsProblem.getString("INPUT"), rsProblem.getString("OUTPUT"));
			rsProblem.close();
			stmt.close();

			// Get language
			stmt = c.createStatement();
			ResultSet rsLanguage = stmt
					.executeQuery("SELECT * FROM LANGUAGE WHERE ID = '"
							+ rs.getInt("LANGUAGE_ID") + "';");
			rsLanguage.next();
			Language language = new Language(rsLanguage.getInt("ID"),
					rsLanguage.getString("NAME"),
					rsLanguage.getString("FILE_EXTENSION"),
					rsLanguage.getString("CMD_COMPILE"),
					rsLanguage.getString("CMD_EXECUTE"));
			rsLanguage.close();
			stmt.close();

			// Get contestant
			stmt = c.createStatement();
			ResultSet rsContestant = stmt
					.executeQuery("SELECT * FROM CONTESTANT WHERE ID = '"
							+ rs.getInt("CONTESTANT_ID") + "';");
			rsContestant.next();
			Contestant contestant = new Contestant(rsContestant.getInt("ID"),
					rsContestant.getInt("PROBLEMS_SOLVED"),
					rsContestant.getInt("PENALTY"),
					rsContestant.getString("USERNAME"),
					rsContestant.getString("PASSWORD"));
			rsContestant.close();
			stmt.close();

			// Get submission
			submission = new Submission(rs.getInt("ID"), problem, language,
					contestant, rs.getString("SOURCE_CODE"),
					rs.getString("OUTPUT"), rs.getInt("TIME"),
					rs.getInt("RESULT"));
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return submission;
	}

	/**
	 * Returns an ArrayList of all submissions, and it takes a Contestant object
	 * as a parameter, it will return the submissions of all that contestant,
	 * but if null was given, it will return all of the submissions regardless
	 * of the contestant.
	 */
	public static synchronized ArrayList<Submission> getAllSubmissions(
			Contestant contestantSearch) {
		Connection c = null;
		Statement stmt = null;
		ArrayList<Submission> submissions = new ArrayList<Submission>();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			ResultSet rs = null;
			if (contestantSearch == null) {
				rs = stmt
						.executeQuery("SELECT * FROM SUBMISSION ORDER BY ID DESC;");
			} else {
				rs = stmt
						.executeQuery("SELECT * FROM SUBMISSION WHERE CONTESTANT_ID = "
								+ contestantSearch.getId()
								+ " ORDER BY ID DESC");
			}
			while (rs.next()) {
				// Get problem
				stmt = c.createStatement();
				ResultSet rsProblem = stmt
						.executeQuery("SELECT * FROM PROBLEM WHERE ID = '"
								+ rs.getInt("PROBLEM_ID") + "';");
				rsProblem.next();
				Problem problem = new Problem(rsProblem.getInt("ID"),
						rsProblem.getString("TITLE"),
						rsProblem.getString("FILE_NAME"),
						rsProblem.getString("INPUT"),
						rsProblem.getString("OUTPUT"));
				rsProblem.close();
				stmt.close();

				// Get language
				stmt = c.createStatement();
				ResultSet rsLanguage = stmt
						.executeQuery("SELECT * FROM LANGUAGE WHERE ID = '"
								+ rs.getInt("LANGUAGE_ID") + "';");
				rsLanguage.next();
				Language language = new Language(rsLanguage.getInt("ID"),
						rsLanguage.getString("NAME"),
						rsLanguage.getString("FILE_EXTENSION"),
						rsLanguage.getString("CMD_COMPILE"),
						rsLanguage.getString("CMD_EXECUTE"));
				rsLanguage.close();
				stmt.close();

				// Get contestant
				stmt = c.createStatement();
				ResultSet rsContestant = stmt
						.executeQuery("SELECT * FROM CONTESTANT WHERE ID = '"
								+ rs.getInt("CONTESTANT_ID") + "';");
				rsContestant.next();
				Contestant contestant = new Contestant(
						rsContestant.getInt("ID"),
						rsContestant.getInt("PROBLEMS_SOLVED"),
						rsContestant.getInt("PENALTY"),
						rsContestant.getString("USERNAME"),
						rsContestant.getString("PASSWORD"));
				rsContestant.close();
				stmt.close();

				// Get submission
				submissions.add(new Submission(rs.getInt("ID"), problem,
						language, contestant, rs.getString("SOURCE_CODE"), rs
								.getString("OUTPUT"), rs.getInt("TIME"), rs
								.getInt("RESULT")));
				stmt.close();
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return submissions;
	}

	/**
	 * Returns an ArrayList of submissions that aren't validated yet. (Pending).
	 */
	public static synchronized ArrayList<Submission> getPendingSubmissions() {
		Connection c = null;
		Statement stmt = null;
		ArrayList<Submission> submissions = new ArrayList<Submission>();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			ResultSet rs = null;
			rs = stmt.executeQuery("SELECT * FROM SUBMISSION WHERE RESULT = "
					+ Submission.RESULT_PENDING);
			while (rs.next()) {
				// Get problem
				stmt = c.createStatement();
				ResultSet rsProblem = stmt
						.executeQuery("SELECT * FROM PROBLEM WHERE ID = '"
								+ rs.getInt("PROBLEM_ID") + "';");
				rsProblem.next();
				Problem problem = new Problem(rsProblem.getInt("ID"),
						rsProblem.getString("TITLE"),
						rsProblem.getString("FILE_NAME"),
						rsProblem.getString("INPUT"),
						rsProblem.getString("OUTPUT"));
				rsProblem.close();
				stmt.close();

				// Get language
				stmt = c.createStatement();
				ResultSet rsLanguage = stmt
						.executeQuery("SELECT * FROM LANGUAGE WHERE ID = '"
								+ rs.getInt("LANGUAGE_ID") + "';");
				rsLanguage.next();
				Language language = new Language(rsLanguage.getInt("ID"),
						rsLanguage.getString("NAME"),
						rsLanguage.getString("FILE_EXTENSION"),
						rsLanguage.getString("CMD_COMPILE"),
						rsLanguage.getString("CMD_EXECUTE"));
				rsLanguage.close();
				stmt.close();

				// Get contestant
				stmt = c.createStatement();
				ResultSet rsContestant = stmt
						.executeQuery("SELECT * FROM CONTESTANT WHERE ID = '"
								+ rs.getInt("CONTESTANT_ID") + "';");
				rsContestant.next();
				Contestant contestant = new Contestant(
						rsContestant.getInt("ID"),
						rsContestant.getInt("PROBLEMS_SOLVED"),
						rsContestant.getInt("PENALTY"),
						rsContestant.getString("USERNAME"),
						rsContestant.getString("PASSWORD"));
				rsContestant.close();
				stmt.close();

				// Get submission
				submissions.add(new Submission(rs.getInt("ID"), problem,
						language, contestant, rs.getString("SOURCE_CODE"), rs
								.getString("OUTPUT"), rs.getInt("TIME"), rs
								.getInt("RESULT")));
				stmt.close();
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return submissions;
	}

	/**
	 * Updates a submission by taking a Submission object.
	 */
	public static synchronized void updateSubmission(Submission o) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = "UPDATE SUBMISSION set PROBLEM_ID = "
					+ o.getProblem().getId() + ", LANGUAGE_ID = "
					+ o.getLanguage().getId() + ", CONTESTANT_ID = "
					+ o.getContestant().getId() + ", SOURCE_CODE = '"
					+ o.getSourceCode() + "', OUTPUT = '" + o.getOutput()
					+ "', TIME = " + o.getTimeSubmitted() + ", RESULT = "
					+ o.getResult() + " where ID=" + o.getId() + ";";
			stmt.executeUpdate(sql);
			c.commit();

			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		// Whenever a submission is updated, the scorebaord should be too
		updateScoreboard();
	}

	/**
	 * Deletes a submission by taking an ID.
	 */
	public static synchronized void deleteSubmission(int id) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = "DELETE from SUBMISSION where ID=" + id + ";";
			stmt.executeUpdate(sql);
			c.commit();

			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * It will update the PROBLEMS_SOLVED and PENALTY fields of all the
	 * contestants based on the submissions table. This will be run each time
	 * updateSubmission(Submission o) is run.
	 */
	private static void updateScoreboard() {
		// Get all the contestants
		ArrayList<Contestant> contestants = getAllContestants();

		// Loop through every contestant
		for (Contestant c : contestants) {
			// Get current contestant's submissions
			ArrayList<Submission> submissions = getAllSubmissions(c);

			// This will hold solved problems uniquely (no duplicates)
			HashSet<Integer> solvedProblems = new HashSet<Integer>();

			// This will hold the penalty
			int penalty = 0;

			// Loop through every submission from the contestant
			for (Submission s : submissions) {
				if (s.getResult() == Submission.RESULT_ACCEPTED) {
					// If accepted add to solved problems list
					solvedProblems.add(s.getProblem().getId());
				} else if (s.getResult() == Submission.RESULT_PENDING) {
					// Skip because not tested yet
					continue;
				} else {
					// Add 20 to penalty if not accepted
					penalty += 1;
				}
			}

			// Count problems solved and set it
			c.setProblemsSolved(solvedProblems.size());

			// Set penalty for current contestant
			c.setPenalty(penalty);

			// Update current contestant in the database
			updateContestant(c, true);
		}
	}

}