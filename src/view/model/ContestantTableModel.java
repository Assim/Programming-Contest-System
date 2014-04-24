package view.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import util.DbAdapter;
import model.Contestant;

/**
 * This model is for holding the data for the JTable for the contestants.
 */
public class ContestantTableModel extends AbstractTableModel implements
		DbTableModel {

	private static final long serialVersionUID = 1L;

	/**
	 * Username column constant.
	 */
	private static final int COLUMN_USERNAME = 0;

	/**
	 * Password column constant.
	 */
	private static final int COLUMN_PASSWORD = 1;

	/**
	 * The list of column names for the JTable.
	 */
	private String[] columnNames;

	/**
	 * ArrayList for holding the rows
	 */
	ArrayList<Contestant> contestants;

	/**
	 * The constructor will set column names and the list of contestants from
	 * the DB.
	 */
	public ContestantTableModel() {
		columnNames = new String[] { "Username", "Password" };
		contestants = DbAdapter.getAllContestants();
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
		case COLUMN_USERNAME:
			value = contestant.getUsername();
			break;
		case COLUMN_PASSWORD:
			value = "******"; // password is hidden
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
		Contestant contestant = contestants.get(rowIndex);

		switch (columnIndex) {
		case COLUMN_USERNAME:
			contestant.setUsername(value.toString());
			break;
		case COLUMN_PASSWORD:
			contestant.setPassword(value.toString());
			break;
		}

		DbAdapter.updateContestant(contestant, false);
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
		DbAdapter.addContestant(new Contestant());
		this.contestants = DbAdapter.getAllContestants();
		this.fireTableDataChanged(); // Update table view
	}

	/**
	 * Deletes a row
	 */
	public void delete(int rowIndex) {
		Contestant contestant = contestants.get(rowIndex);
		DbAdapter.deleteContestant(contestant.getId());
		this.contestants = DbAdapter.getAllContestants();
		this.fireTableDataChanged(); // Update table view
	}

	/**
	 * Set contestants list
	 */
	public void setContestants(ArrayList<Contestant> contestants) {
		this.contestants = contestants;
		this.fireTableDataChanged(); // update JTable
	}

	/**
	 * Returns contestants list
	 */
	public ArrayList<Contestant> getContestants() {
		return this.contestants;
	}

}