package view.common;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import view.client.ClientView;
import view.model.SubmissionTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This SubmissionView JPanel is used by both the server and client. It will
 * show the list of submissions and results and it's data will be supplied by
 * the SubmissionTableModel. The client will show logged in contestant
 * submissions. The server will show all the submissions together.
 */
public class SubmissionView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JButton btnSubmitCode;
	public static final int AS_SERVER = 0;
	public static final int AS_CLIENT = 1;

	/**
	 * Create the panel.
	 */
	public SubmissionView(int mode, final ClientView cv,
			SubmissionTableModel model) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JScrollPane scrollPane = new JScrollPane();

		// If client
		if (mode == AS_CLIENT) {
			// Show submit button
			btnSubmitCode = new JButton("Submit Code");
			btnSubmitCode.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Show the submit form
					cv.submitMode(true);
				}
			});
			btnSubmitCode.setAlignmentX(Component.CENTER_ALIGNMENT);
			add(btnSubmitCode);
		}

		table = new JTable();
		table.setModel(model);

		scrollPane.getViewport().add(table, null);

		add(scrollPane);

	}

	/**
	 * This method is used for enabling/disabling the submit button.
	 */
	public void enableSubmit(boolean value) {
		btnSubmitCode.setEnabled(value);
	}

}