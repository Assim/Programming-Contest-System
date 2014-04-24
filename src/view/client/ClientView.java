package view.client;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import rmi.RmiData;
import main.Client;
import util.Utilities;
import view.common.ScoreboardView;
import view.common.SubmissionView;
import view.help.AboutView;
import view.help.HowToUseView;
import view.model.ScoreboardTableModel;
import view.model.SubmissionTableModel;

import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This view is the main view for the client once the contestants logs in. It's
 * also a container for other views.
 */
public class ClientView {

	/**
	 * This is the client object that controls this JFrame.
	 */
	private Client client;

	/**
	 * The SubmissionView JPanel that will be displayed in the tabbed pane.
	 */
	private SubmissionView submissionView;

	/**
	 * The ScoreboardView JPanel that will be displayed in the tabbed pane.
	 */
	private ScoreboardView scoreboardView;

	/**
	 * The ScoreboardTableModel will represent the scoreboard data.
	 */
	private ScoreboardTableModel scoreboardModel;

	/**
	 * The SubmissionTableModel will represent the contestant submission data.
	 */
	private SubmissionTableModel submissionsModel;

	private JFrame frmProgrammingContestSystem;
	private JScrollPane submissionPane;
	private JScrollPane scoreboardPane;
	private JLabel lblLoggedInAs;
	private JLabel lblTimeLeft;
	private JMenuItem mntmAbout;
	private JMenuItem mntmHowToUse;
	private JMenu mnFile;
	private JMenuItem mntmReset;
	private JMenuItem mntmExit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ClientView window = new ClientView(null, null);
					window.frmProgrammingContestSystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientView(Client client, String username) {
		this.client = client;
		this.scoreboardModel = new ScoreboardTableModel();
		this.submissionsModel = new SubmissionTableModel();
		this.submissionView = new SubmissionView(SubmissionView.AS_CLIENT,
				this, this.submissionsModel);
		this.scoreboardView = new ScoreboardView(this.scoreboardModel);
		initialize();
		setContestantName(username);
		updateViews();
	}

	/**
	 * Returns frame
	 */
	public JFrame getFrame() {
		return frmProgrammingContestSystem;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmProgrammingContestSystem = new JFrame();
		frmProgrammingContestSystem.setTitle("Programming Contest System");
		frmProgrammingContestSystem.setBounds(100, 100, 450, 546);
		frmProgrammingContestSystem
				.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmProgrammingContestSystem.setJMenuBar(menuBar);

		mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mntmReset = new JMenuItem("Reset");
		mntmReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Utilities.resetSettings();
			}
		});
		mnFile.add(mntmReset);

		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AboutView().setVisible(true);
			}
		});
		mnHelp.add(mntmAbout);

		mntmHowToUse = new JMenuItem("How to use?");
		mntmHowToUse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new HowToUseView().setVisible(true);
			}
		});
		mnHelp.add(mntmHowToUse);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		frmProgrammingContestSystem.getContentPane().setLayout(gridBagLayout);

		JLabel lblProgrammingContestSystem = new JLabel(
				"Programming Contest System");
		lblProgrammingContestSystem.setFont(new Font("Lucida Grande",
				Font.PLAIN, 20));
		GridBagConstraints gbc_lblProgrammingContestSystem = new GridBagConstraints();
		gbc_lblProgrammingContestSystem.insets = new Insets(5, 5, 5, 0);
		gbc_lblProgrammingContestSystem.gridx = 0;
		gbc_lblProgrammingContestSystem.gridy = 0;
		gbc_lblProgrammingContestSystem.gridwidth = 3;
		frmProgrammingContestSystem.getContentPane().add(
				lblProgrammingContestSystem, gbc_lblProgrammingContestSystem);

		lblTimeLeft = new JLabel("Time Left: 5:00");
		GridBagConstraints gbc_lblTimeLeft = new GridBagConstraints();
		gbc_lblTimeLeft.insets = new Insets(5, 5, 5, 5);
		gbc_lblTimeLeft.gridx = 0;
		gbc_lblTimeLeft.gridy = 1;
		frmProgrammingContestSystem.getContentPane().add(lblTimeLeft,
				gbc_lblTimeLeft);

		lblLoggedInAs = new JLabel("");
		GridBagConstraints gbc_lblLoggedInAs = new GridBagConstraints();
		gbc_lblLoggedInAs.insets = new Insets(5, 5, 5, 5);
		gbc_lblLoggedInAs.gridx = 1;
		gbc_lblLoggedInAs.gridy = 1;
		frmProgrammingContestSystem.getContentPane().add(lblLoggedInAs,
				gbc_lblLoggedInAs);

		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		GridBagConstraints gbc_btnLogout = new GridBagConstraints();
		gbc_btnLogout.insets = new Insets(5, 5, 5, 0);
		gbc_btnLogout.gridx = 2;
		gbc_btnLogout.gridy = 1;
		frmProgrammingContestSystem.getContentPane().add(btnLogout,
				gbc_btnLogout);

		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.insets = new Insets(5, 5, 5, 5);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 2;
		gbc_tabbedPane.gridwidth = 3;
		frmProgrammingContestSystem.getContentPane().add(tabbedPane,
				gbc_tabbedPane);

		submissionPane = new JScrollPane(submissionView);
		tabbedPane.addTab("Submissions", null, submissionPane, null);

		scoreboardPane = new JScrollPane(scoreboardView);
		tabbedPane.addTab("Scoreboard", null, scoreboardPane, null);
	}

	/**
	 * Submit mode allows to choose whether to show list of user submission or
	 * submit form. If true, it will show the form. Otherwise the list of
	 * submissions. Both of those views will use this to interchange between
	 * themselves.
	 */
	public void submitMode(boolean isSubmitMode) {
		if (isSubmitMode) {
			submissionPane.setViewportView(new SubmitView(this));
		} else {
			submissionPane.setViewportView(submissionView);
		}
	}

	/**
	 * Sets the contestant name on the frame.
	 */
	public void setContestantName(String username) {
		lblLoggedInAs.setText("Logged in as: " + username);
	}

	/**
	 * Sets the time left on the frame.
	 */
	public void setTimeLeft(String timeLeft) {
		lblTimeLeft.setText("Time Left: " + timeLeft);
	}

	/**
	 * Updates the views and the models based on RMI data.
	 */
	public void updateViews() {
		RmiData socketData = new RmiData(); // Get RMI data

		// Update models
		scoreboardModel.setContestants(socketData.getContestants());
		submissionsModel.setSubmissions(socketData.getSubmissions());

		// Set time left
		setTimeLeft(socketData.getTimeLeft());

		// If contest was running, show submit button, otherwise disable it
		submissionView.enableSubmit(socketData.getContestRunning());
	}

	/**
	 * Returns the client object (controller) that is running.
	 */
	public Client getClient() {
		return this.client;
	}

}