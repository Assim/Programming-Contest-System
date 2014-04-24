package view.server;

import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.JComboBox;

import java.awt.GridBagConstraints;

import javax.swing.JButton;

import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.DefaultComboBoxModel;

import view.model.ContestantTableModel;
import view.model.DbTableModel;
import view.model.LanguageTableModel;
import view.model.ProblemTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * The SettingsView will allow the contest admin to perform CRUD operations on
 * the database by updating contestants, problems, and languages.
 */
public class SettingsView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public SettingsView() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 235, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (comboBox.getSelectedIndex()) {
				case 0:
					table.setModel(new ContestantTableModel());
					break;
				case 1:
					table.setModel(new ProblemTableModel());
					break;
				case 2:
					table.setModel(new LanguageTableModel());
					break;
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Contestants", "Problems", "Languages" }));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 0;
		gbc_comboBox.gridwidth = 2;
		add(comboBox, gbc_comboBox);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		gbc_scrollPane.gridwidth = 2;
		add(scrollPane, gbc_scrollPane);

		table = new JTable();
		/*
		 * GridBagConstraints gbc_table = new GridBagConstraints();
		 * gbc_table.insets = new Insets(0, 0, 5, 0); gbc_table.fill =
		 * GridBagConstraints.BOTH; gbc_table.gridx = 0; gbc_table.gridy = 1;
		 * gbc_table.gridwidth = 2; add(table, gbc_table);
		 */
		table.setModel(new ContestantTableModel());

		scrollPane.getViewport().add(table, null);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Add to current table
				// Using DbTableModel interface to have a super type for all
				DbTableModel model = (DbTableModel) table.getModel();
				model.add();
			}
		});
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.insets = new Insets(0, 0, 0, 5);
		gbc_btnAdd.gridx = 0;
		gbc_btnAdd.gridy = 2;
		add(btnAdd, gbc_btnAdd);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Delete row from current table
				// Using DbTableModel interface to have a super type for all
				DbTableModel model = (DbTableModel) table.getModel();
				model.delete(table.getSelectedRow());
			}
		});
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.gridx = 1;
		gbc_btnDelete.gridy = 2;
		add(btnDelete, gbc_btnDelete);

	}

}