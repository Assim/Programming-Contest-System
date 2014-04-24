package view.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import model.Contestant;

/**
 * This model is for holding the data for the JTable for the scoreboard.
 */
public class ScoreboardTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	/**
	 * Contestant column constant.
	 */
	private static final int COLUMN_CONTESTANT = 0;

	/**
	 * Problems solved column constant.
	 */
	private static final int COLUMN_PROBLEMS_SOLVED = 1;

	/**
	 * Penalty column constant.
	 */
	private static final int COLUMN_PENALTY = 2;

	/**
	 * The list of contestants.
	 */
	private ArrayList<Contestant> contestants;

	/**
	 * The list of column names for the JTable.
	 */
	private String[] columnNames;

	/**
	 * The constructor will set column names and Scoreboard object.
	 */
	public ScoreboardTableModel() {
		columnNames = new String[] { "Contestant", "Problems Solved", "Penalty" };
		contestants = new ArrayList<Contestant>();
	}

	@Override
	/**
	 * Returns the number of rows in the table.
	 */
	public int getRowCount() {
		return this.contestants.size();
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

		Contestant contestant = contestants.get(rowIndex);

		switch (columnIndex) {
		case COLUMN_CONTESTANT:
			value = contestant.getUsername();
			break;
		case COLUMN_PROBLEMS_SOLVED:
			value = contestant.getProblemsSolved();
			break;
		case COLUMN_PENALTY:
			value = contestant.getPenalty();
			break;
		}

		return value;
	}

	/**
	 * This method will be used by the table component when user edits a given
	 * cell at [row, column]. The corresponding contestant object will be
	 * updated.
	 */
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		// Code commented because there isn't a need to changing the values.
		/*
		 * Contestant team = scoreboard.getContestants().get(rowIndex);
		 * 
		 * switch(columnIndex) { case COLUMN_CONTESTANT:
		 * team.setContestantName(value.toString()); break; case
		 * COLUMN_PROBLEMS_SOLVED:
		 * team.setProblemsSolved(Integer.parseInt((String) value)); break; case
		 * COLUMN_PENALTY: team.setPenalty(Integer.parseInt((String) value));
		 * break; } this.fireTableDataChanged(); // update JTable
		 */
	}

	/**
	 * Set the Scoreboard object to the model.
	 */
	public void setContestants(ArrayList<Contestant> contestants) {
		this.contestants = contestants;
		this.fireTableDataChanged(); // update JTable
	}

	/**
	 * Returns the Scoreboard object.
	 */
	public ArrayList<Contestant> getContestants() {
		return this.contestants;
	}

}