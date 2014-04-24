package view.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import util.DbAdapter;
import model.Problem;

/**
 * This model is for holding the data for the JTable for the problems.
 */
public class ProblemTableModel extends AbstractTableModel implements
		DbTableModel {

	private static final long serialVersionUID = 1L;

	/**
	 * Title column constant.
	 */
	private static final int COLUMN_TITLE = 0;

	/**
	 * File name column constant.
	 */
	private static final int COLUMN_FILE_NAME = 1;

	/**
	 * Input column constant.
	 */
	private static final int COLUMN_INPUT = 2;

	/**
	 * Output column constant.
	 */
	private static final int COLUMN_OUTPUT = 3;

	/**
	 * The list of column names for the JTable.
	 */
	private String[] columnNames;

	/**
	 * ArrayList for holding the rows
	 */
	ArrayList<Problem> problems;

	/**
	 * The constructor will set column names and the list of problems from the
	 * DB.
	 */
	public ProblemTableModel() {
		columnNames = new String[] { "Title", "File name", "Input", "Output" };
		problems = DbAdapter.getAllProblems();
	}

	@Override
	/**
	 * Returns the number of rows in the table.
	 */
	public int getRowCount() {
		return this.problems.size();
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

		Problem problem = problems.get(rowIndex);

		switch (columnIndex) {
		case COLUMN_TITLE:
			value = problem.getProblemName();
			break;
		case COLUMN_FILE_NAME:
			value = problem.getFilename();
			break;
		case COLUMN_INPUT:
			value = problem.getInput();
			break;
		case COLUMN_OUTPUT:
			value = problem.getOutput();
			break;
		}

		return value;
	}

	/**
	 * This method will be used by the table component when user edits a given
	 * cell at [row, column]. The corresponding problem object will be updated.
	 */
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		Problem problem = problems.get(rowIndex);

		switch (columnIndex) {
		case COLUMN_TITLE:
			problem.setProblemName(value.toString());
			break;
		case COLUMN_FILE_NAME:
			problem.setFileName(value.toString());
			break;
		case COLUMN_INPUT:
			problem.setInput(value.toString());
			break;
		case COLUMN_OUTPUT:
			problem.setOutput(value.toString());
			break;
		}

		DbAdapter.updateProblem(problem);
		this.fireTableDataChanged(); // update JTable
	}

	/**
	 * To make all cells editable
	 */
	public boolean isCellEditable(int row, int col) {
		return true;
	}

	/**
	 * Adds an empty row
	 */
	public void add() {
		DbAdapter.addProblem(new Problem());
		this.problems = DbAdapter.getAllProblems();
		this.fireTableDataChanged(); // Update table view
	}

	/**
	 * Deletes a row
	 */
	public void delete(int rowIndex) {
		Problem problem = problems.get(rowIndex);
		DbAdapter.deleteProblem(problem.getId());
		this.problems = DbAdapter.getAllProblems();
		this.fireTableDataChanged(); // Update table view
	}

	/**
	 * Sets the problems list
	 */
	public void setProblems(ArrayList<Problem> problems) {
		this.problems = problems;
		this.fireTableDataChanged(); // update JTable
	}

	/**
	 * Returns a list of problems
	 */
	public ArrayList<Problem> getProblems() {
		return this.problems;
	}

}