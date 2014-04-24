package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import model.Submission;

/**
 * This class is in charge of taking the getting a pending submission, compile
 * it, run it, validate it. It will give the user submission the input file, and
 * then it will compare the user submission output with the expected output, and
 * it will return the result based on that.
 */
public final class CodeRunner {

	/**
	 * This is where the directory name where the code compilation and running
	 * will take place.
	 */
	private static final String WORKING_DIRECTORY = "pcstemp";

	/**
	 * Private constructor because it's a util class, no need for instantiation
	 */
	private CodeRunner() {
	}

	/**
	 * Runs a command on the command line in the working directory.
	 */
	private static int runProcess(String command) throws Exception {
		Process process = Runtime.getRuntime().exec(command, null,
				new File(WORKING_DIRECTORY));
		printProcessLines(command + " stdout:", process.getInputStream());
		printProcessLines(command + " stderr:", process.getErrorStream());
		process.waitFor();
		return process.exitValue();
	}

	/**
	 * Prints texts from streams after running a command on the command line.
	 */
	private static void printProcessLines(String name, InputStream ins)
			throws Exception {
		String line = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(ins));
		while ((line = in.readLine()) != null) {
			System.out.println(name + " " + line);
		}
	}

	/**
	 * Takes a submission and runs and validates it and returns the same object
	 * with the modified output and result
	 */
	public synchronized static Submission runAndValidate(Submission submission) {
		// Re-usable objects
		PrintWriter out = null;
		String resultFileChecksum = null;
		String outputFileChecksum = null;
		int compileResult = -1;
		int executeResult = -1;

		// Create working directory
		File dir = new File(WORKING_DIRECTORY);
		dir.mkdir();

		// Create the input, output, and result, and submission file objects in
		// the working
		// directory
		File inputFile = new File(dir.getAbsolutePath() + File.separator
				+ "input.in");
		File outputFile = new File(dir.getAbsolutePath() + File.separator
				+ "output.out");
		File resultFile = new File(dir.getAbsolutePath() + File.separator
				+ "result.out");
		File submissionFile = new File(dir.getAbsoluteFile() + File.separator
				+ submission.getProblem().getFilename() + "."
				+ submission.getLanguage().getFileExtension());

		// Create input file only, output will be created later to prevent
		// cheating
		try {
			inputFile.createNewFile();
			out = new PrintWriter(inputFile);
			out.print(submission.getProblem().getInput());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}

		// Create user submission file
		try {
			submissionFile.createNewFile();
			out = new PrintWriter(submissionFile);
			out.print(submission.getSourceCode());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}

		// Convert language variables to values
		String cmdCompile = replaceLanguageVars(submission.getLanguage()
				.getCmdCompile(), submission);
		String cmdExecute = replaceLanguageVars(submission.getLanguage()
				.getCmdExecute(), submission);

		// Compile and execute program
		try {
			compileResult = runProcess(cmdCompile);
			executeResult = runProcess(cmdExecute);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Create the results file
		// It was created at this point to prevent cheating
		try {
			resultFile.createNewFile();
			out = new PrintWriter(resultFile);
			out.print(submission.getProblem().getOutput());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}

		// Store user output in submission object
		if (outputFile.exists()) {
			BufferedReader br;
			StringBuffer sb = new StringBuffer();
			String line = null;
			try {
				br = new BufferedReader(new FileReader(outputFile));
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				submission.setOutput(sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Get the checksum of the output file and result file
			try {
				resultFileChecksum = getChecksum(resultFile.toString());
				outputFileChecksum = getChecksum(outputFile.toString());
			} catch (NoSuchAlgorithmException | IOException e) {
				e.printStackTrace();
			}
		}

		// Determine result
		if (compileResult != 0)
			submission.setResult(Submission.RESULT_COMPILATION_ERROR);
		else if (executeResult != 0)
			submission.setResult(Submission.RESULT_RUNTIME_ERROR);
		else {
			if (outputFile.exists()) { // Check if output file was created
				if (resultFileChecksum.equals(outputFileChecksum)) // Compare
																	// files
					submission.setResult(Submission.RESULT_ACCEPTED); // Return
																		// as
																		// accepted
				else
					submission.setResult(Submission.RESULT_REJECTED); // Return
																		// as
																		// rejected
			} else
				submission.setResult(Submission.RESULT_NO_OUTPUT); // Return as
																	// no
																	// output
		}

		// Delete directory after use
		Utilities.deleteDirectory(dir);

		return submission;
	}

	/**
	 * Replace the language variables with the appropriate values
	 */
	private static String replaceLanguageVars(String str, Submission s) {
		return str.replaceAll("%NAME%", s.getProblem().getFilename());
	}

	/**
	 * This will create the checksum by getting the bytes from the file, it
	 * doesn't need to be called It will be called automatically by the
	 * getChecksum method.
	 */
	private static byte[] createChecksum(String filename)
			throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		InputStream fis = new FileInputStream(filename);

		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;
		do {
			numRead = fis.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);
		fis.close();
		return complete.digest();
	}

	/**
	 * This function is the function that will be used to calculate the
	 * checksum, pass a file, and it'll return the checksum.
	 */
	private static String getChecksum(String filename)
			throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		byte[] b = createChecksum(filename);
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

}