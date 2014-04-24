package view.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import util.DbAdapter;
import model.Language;

/**
 * This model is for holding the data for the JTable for the languages.
 */
public class LanguageTableModel extends AbstractTableModel implements
		DbTableModel {

	private static final long serialVersionUID = 1L;

	/**
	 * Title column constant.
	 */
	private static final int COLUMN_NAME = 0;

	/**
	 * File name column constant.
	 */
	private static final int COLUMN_FILE_EXTENSION = 1;

	/**
	 * Input column constant.
	 */
	private static final int COLUMN_CMD_COMPILE = 2;

	/**
	 * Output column constant.
	 */
	private static final int COLUMN_CMD_EXECUTE = 3;

	/**
	 * The list of column names for the JTable.
	 */
	private String[] columnNames;

	/**
	 * ArrayList for holding the rows
	 */
	ArrayList<Language> languages;

	/**
	 * The constructor will set column names and the list of problems from the
	 * DB.
	 */
	public LanguageTableModel() {
		columnNames = new String[] { "Name", "File ext", "Compile Command",
				"Execute Command" };
		languages = DbAdapter.getAllLanguages();
	}

	@Override
	/**
	 * Returns the number of rows in the table.
	 */
	public int getRowCount() {
		return this.languages.size();
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

		Language language = languages.get(rowIndex);

		switch (columnIndex) {
		case COLUMN_NAME:
			value = language.getName();
			break;
		case COLUMN_FILE_EXTENSION:
			value = language.getFileExtension();
			break;
		case COLUMN_CMD_COMPILE:
			value = language.getCmdCompile();
			break;
		case COLUMN_CMD_EXECUTE:
			value = language.getCmdExecute();
			break;
		}

		return value;
	}

	/**
	 * This method will be used by the table component when user edits a given
	 * cell at [row, column]. The corresponding language object will be updated.
	 */
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		Language language = languages.get(rowIndex);

		switch (columnIndex) {
		case COLUMN_NAME:
			language.setName(value.toString());
			break;
		case COLUMN_FILE_EXTENSION:
			language.setFileExtension(value.toString());
			break;
		case COLUMN_CMD_COMPILE:
			language.setCmdCompile(value.toString());
			break;
		case COLUMN_CMD_EXECUTE:
			language.setCmdExecute(value.toString());
			break;
		}

		DbAdapter.updateLanguage(language);
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
		DbAdapter.addLanguage(new Language());
		this.languages = DbAdapter.getAllLanguages();
		this.fireTableDataChanged(); // Update table view
	}

	/**
	 * Deletes a row
	 */
	public void delete(int rowIndex) {
		Language language = languages.get(rowIndex);
		DbAdapter.deleteLanguage(language.getId());
		this.languages = DbAdapter.getAllLanguages();
		this.fireTableDataChanged(); // Update table view
	}

	/**
	 * Sets languages list
	 */
	public void setLanguages(ArrayList<Language> languages) {
		this.languages = languages;
		this.fireTableDataChanged(); // update JTable
	}

	/**
	 * Returns languages list
	 */
	public ArrayList<Language> getLanguages() {
		return this.languages;
	}

}