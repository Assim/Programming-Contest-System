package view.client;

import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import rmi.RmiData;
import model.Language;
import model.Problem;
import model.Submission;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * This is the JPanel that will be displayed when user wants to submit to the
 * server. It'll be displayed in the ClientView.
 */
public class SubmitView extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * This is it's parent object, it will be used when user wants to send or
	 * cancel a submission.
	 */
	private ClientView cv;

	private JComboBox<String> problemComboBox;
	private JComboBox<String> languageComboBox;
	private JTextArea textArea;
	private JButton btnSubmit;

	/**
	 * Create the panel.
	 */
	public SubmitView(final ClientView cv) {
		this.cv = cv;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblProblem = new JLabel("Problem");
		GridBagConstraints gbc_lblProblem = new GridBagConstraints();
		gbc_lblProblem.anchor = GridBagConstraints.EAST;
		gbc_lblProblem.insets = new Insets(0, 0, 5, 5);
		gbc_lblProblem.gridx = 0;
		gbc_lblProblem.gridy = 0;
		add(lblProblem, gbc_lblProblem);

		problemComboBox = new JComboBox<String>();
		GridBagConstraints gbc_problemComboBox = new GridBagConstraints();
		gbc_problemComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_problemComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_problemComboBox.gridx = 1;
		gbc_problemComboBox.gridy = 0;
		gbc_problemComboBox.gridwidth = 2;
		add(problemComboBox, gbc_problemComboBox);

		JLabel lblLanguage = new JLabel("Language");
		GridBagConstraints gbc_lblLanguage = new GridBagConstraints();
		gbc_lblLanguage.anchor = GridBagConstraints.EAST;
		gbc_lblLanguage.insets = new Insets(0, 0, 5, 5);
		gbc_lblLanguage.gridx = 0;
		gbc_lblLanguage.gridy = 1;
		add(lblLanguage, gbc_lblLanguage);

		languageComboBox = new JComboBox<String>();
		GridBagConstraints gbc_languageComboBox = new GridBagConstraints();
		gbc_languageComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_languageComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_languageComboBox.gridx = 1;
		gbc_languageComboBox.gridy = 1;
		gbc_languageComboBox.gridwidth = 2;
		add(languageComboBox, gbc_languageComboBox);

		JLabel lblSourceCode = new JLabel("Source Code");
		GridBagConstraints gbc_lblSourceCode = new GridBagConstraints();
		gbc_lblSourceCode.insets = new Insets(0, 0, 5, 5);
		gbc_lblSourceCode.gridx = 0;
		gbc_lblSourceCode.gridy = 2;
		add(lblSourceCode, gbc_lblSourceCode);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// When user clicks on cancel, this will let the ClientView hide
				// the view.
				cv.submitMode(false);
			}
		});

		textArea = new JTextArea();
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 2;
		gbc_textArea.gridwidth = 2;
		add(textArea, gbc_textArea);

		btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submit();
			}
		});
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.insets = new Insets(0, 0, 0, 5);
		gbc_btnSubmit.gridx = 1;
		gbc_btnSubmit.gridy = 3;
		add(btnSubmit, gbc_btnSubmit);
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.gridx = 2;
		gbc_btnCancel.gridy = 3;
		add(btnCancel, gbc_btnCancel);

		// Update the view
		updateView();
	}

	/**
	 * This method will get the latest data sent through RMI and will update the
	 * form based on that data.
	 */
	public void updateView() {
		// Get the models for the combo boxes
		DefaultComboBoxModel<String> problemsModel = (DefaultComboBoxModel<String>) problemComboBox
				.getModel();
		DefaultComboBoxModel<String> languagesModel = (DefaultComboBoxModel<String>) languageComboBox
				.getModel();

		// Get problems and languages from RMI data
		RmiData socketData = new RmiData();
		ArrayList<Problem> problems = socketData.getProblems();
		ArrayList<Language> languages = socketData.getLanguages();

		// If there aren't any problems or languages added by the server
		if (problems.size() == 0 || languages.size() == 0) {
			// Disable all components except cancel button
			// We need the cancel button so user can go to the previous state
			btnSubmit.setEnabled(false);
			problemComboBox.setEnabled(false);
			languageComboBox.setEnabled(false);
			textArea.setEnabled(false);

			// Display a message for notifying the contestant about the issue.
			JOptionPane
					.showMessageDialog(
							null,
							"Contest Admin hasn't configured the contest yet. Click on Cancel then try again later.");
		}

		// Clear models
		problemsModel.removeAllElements();
		languagesModel.removeAllElements();

		// Fill models with data
		for (Problem p : problems)
			problemsModel.addElement(p.getProblemName());

		for (Language l : languages)
			languagesModel.addElement(l.getName());
	}

	/**
	 * This will take the submission and send it to the server. It will also
	 * automatically switch this view.
	 */
	public void submit() {
		// Get RMI data
		RmiData socketData = new RmiData();

		// Get list of problems
		ArrayList<Problem> problems = socketData.getProblems();
		ArrayList<Language> languages = socketData.getLanguages();

		// Get selected problem and language
		Problem problem = problems.get(problemComboBox.getSelectedIndex());
		Language language = languages.get(languageComboBox.getSelectedIndex());

		// Create a new submission object and set some values
		Submission submission = new Submission();
		submission.setProblem(problem);
		submission.setLanguage(language);
		submission.setSourceCode(textArea.getText()); // get data from text area

		// Send to Client object
		if (cv.getClient().submit(submission)) {
			cv.submitMode(false);
		}
	}

}