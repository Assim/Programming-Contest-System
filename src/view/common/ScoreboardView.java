package view.common;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import view.model.ScoreboardTableModel;

/**
 * This JPanel is used by both the client and the server. It will display the
 * standings and the data will be supplied by the ScoreboardTableModel.
 */
public class ScoreboardView extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTable table;

	/**
	 * Create the panel.
	 */
	public ScoreboardView(ScoreboardTableModel model) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JScrollPane scrollPane = new JScrollPane();

		table = new JTable();
		table.setModel(model);

		scrollPane.getViewport().add(table, null);
		add(scrollPane);
	}

}