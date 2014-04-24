package view.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import util.Utilities;
import model.Submission;

/**
 * This model is for holding the data for the JTable for the submission.
 */
public class SubmissionTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	/**
	 * Problem column constant.
	 */
	private static final int COLUMN_PROBLEM = 0;

	/**
	 * Language column constant.
	 */
	private static final int COLUMN_LANGUAGE = 1;

	/**
	 * Contestant column constant.
	 */
	private static final int COLUMN_CONTESTANT = 2;

	/**
	 * Time submitted column constant.
	 */
	private static final int COLUMN_TIME_SUBMITTED = 3;

	/**
	 * Result column constant.
	 */
	private static final int COLUMN_RESULT = 4;

	/**
	 * The Submission object.
	 */
	private ArrayList<Submission> submissions = new ArrayList<Submission>();

	/**
	 * The list of column names for the JTable.
	 */
	private String[] columnNames;

	/**
	 * The constructor will set column names and Submission object.
	 */
	public SubmissionTableModel() {
		columnNames = new String[] { "Problem", "Language", "Contestant",
				"Time", "Result" };
	}

	@Override
	/**
	 * Returns the number of rows in the table.
	 */
	public int getRowCount() {
		return this.submissions.size();
	}

	@Override
	/**
	 * Returns the number of columns in the table.
	 */
	public int getColumnCount() {
		return this.columnNames.length;
	}

	/**
	 * Returns name of a specified column in the table.
	 */
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	/**
	 * This method will be used by the table component to get
	 * value of a given cell at [row, column]
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object value = null;

		Submission submission = submissions.get(rowIndex);

		switch (columnIndex) {
		case COLUMN_PROBLEM:
			value = submission.getProblem().getProblemName();
			break;
		case COLUMN_LANGUAGE:
			value = submission.getLanguage().getName();
			break;
		case COLUMN_CONTESTANT:
			value = submission.getContestant().getUsername();
			break;
		case COLUMN_TIME_SUBMITTED:
			value = Utilities.getDatetime(submission.getTimeSubmitted());
			break;
		case COLUMN_RESULT:
			value = Submission.getResultName(submission.getResult());
			break;
		}

		return value;
	}

	/**
	 * This method will be used by the table component when user edits a given
	 * cell at [row, column]. The corresponding submission object will be
	 * updated.
	 */
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		// Code commented because there isn't a need to changing the values.
		/*
		 * Submission submission = submissionList.get(rowIndex);
		 * 
		 * switch (columnIndex) { case COLUMN_PROBLEM:
		 * submission.getProblem().setProblemName(value.toString()); break; case
		 * COLUMN_LANGUAGE: submission.getLanguage().setName(value.toString());
		 * break; case COLUMN_CONTESTANT:
		 * submission.getContestant().setContestantName(value.toString());
		 * break; case COLUMN_TIME_SUBMITTED:
		 * submission.setTimeSubmitted(Long.parseLong(value.toString())); break;
		 * case COLUMN_RESULT: submission.getResult().setName(value.toString());
		 * break; } this.fireTableDataChanged(); // update JTable
		 */
	}

	/**
	 * Set the table model's submissions ArrayList
	 */
	public void setSubmissions(ArrayList<Submission> submissions) {
		this.submissions = submissions;
		this.fireTableDataChanged(); // update JTable
	}

	/**
	 * Returns the table model's submissions as an ArrayList
	 */
	public ArrayList<Submission> getSubmissions() {
		return this.submissions;
	}

}