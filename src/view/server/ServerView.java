package view.server;

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

import util.DbAdapter;
import util.PDFGenerator;
import util.Utilities;
import view.common.ScoreboardView;
import view.common.SubmissionView;
import view.help.AboutView;
import view.help.HowToUseView;
import view.model.ContestantTableModel;
import view.model.LanguageTableModel;
import view.model.ProblemTableModel;
import view.model.ScoreboardTableModel;
import view.model.SubmissionTableModel;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import main.Server;
import model.ContestTime;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;

/**
 * This JFrame is the main view for the server, and it is also a container for
 * other views that will be used by the contest admin.
 */
public class ServerView {

	private JFrame frmProgrammingContestSystem;

	/**
	 * This table model will hold data for the scoreboard that will be displayed
	 * in the JTable.
	 */
	private ScoreboardTableModel scoreboardModel;

	/**
	 * This table model will hold data for all the submissions, and will be
	 * displayed in the JTable.
	 */
	private SubmissionTableModel submissionModel;

	/**
	 * This table model will hold data for all the contestants, and will
	 * displayed in the JTable.
	 */
	private ContestantTableModel contestantModel;

	/**
	 * This table model will hold data for all the languages and will be
	 * displayed in the JTable.
	 */
	private LanguageTableModel languageModel;

	/**
	 * This table model will hold data for all the problems and will displayed
	 * in the JTable.
	 */
	private ProblemTableModel problemModel;

	/**
	 * The TimeControlView will be the view for setting the contest time.
	 */
	private TimeControlView timeControlView;

	private JScrollPane submissionsPane;
	private JScrollPane scoreboardPane;
	private JScrollPane settingsPane;
	private JScrollPane timeControlPane;

	private JLabel lblTimeLeft;
	private JMenuItem mntmAbout;
	private JMenuItem mntmHowToUse;
	private JMenu mnFile;
	private JMenuItem mntmReset;
	private JMenuItem mntmExit;
	private JMenu mnReport;
	private JMenuItem mntmGenerateReport;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ServerView window = new ServerView(null);
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
	public ServerView(Server server) {
		this.scoreboardModel = new ScoreboardTableModel();
		this.submissionModel = new SubmissionTableModel();
		this.contestantModel = new ContestantTableModel();
		this.languageModel = new LanguageTableModel();
		this.problemModel = new ProblemTableModel();
		this.timeControlView = new TimeControlView(this);
		initialize();
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
		frmProgrammingContestSystem.setBounds(100, 100, 600, 750);
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

		mnReport = new JMenu("Report");
		menuBar.add(mnReport);

		mntmGenerateReport = new JMenuItem("Generate Report");
		mntmGenerateReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PDFGenerator.generateReport();
			}
		});
		mnReport.add(mntmGenerateReport);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		frmProgrammingContestSystem.getContentPane().setLayout(gridBagLayout);

		JLabel lblProgrammingContestControl = new JLabel(
				"Programming Contest Control");
		lblProgrammingContestControl.setFont(new Font("Lucida Grande",
				Font.PLAIN, 20));
		GridBagConstraints gbc_lblProgrammingContestControl = new GridBagConstraints();
		gbc_lblProgrammingContestControl.insets = new Insets(5, 5, 5, 0);
		gbc_lblProgrammingContestControl.gridx = 0;
		gbc_lblProgrammingContestControl.gridy = 0;
		gbc_lblProgrammingContestControl.gridwidth = 3;
		frmProgrammingContestSystem.getContentPane().add(
				lblProgrammingContestControl, gbc_lblProgrammingContestControl);

		lblTimeLeft = new JLabel("Time Left: 5:00");
		GridBagConstraints gbc_lblTimeLeft = new GridBagConstraints();
		gbc_lblTimeLeft.insets = new Insets(5, 5, 5, 5);
		gbc_lblTimeLeft.gridx = 0;
		gbc_lblTimeLeft.gridy = 1;
		frmProgrammingContestSystem.getContentPane().add(lblTimeLeft,
				gbc_lblTimeLeft);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateViews();
			}
		});
		GridBagConstraints gbc_btnRefresh = new GridBagConstraints();
		gbc_btnRefresh.insets = new Insets(5, 5, 5, 5);
		gbc_btnRefresh.gridx = 1;
		gbc_btnRefresh.gridy = 1;
		gbc_btnRefresh.gridwidth = 2;
		frmProgrammingContestSystem.getContentPane().add(btnRefresh,
				gbc_btnRefresh);

		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
			}
		});
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.insets = new Insets(5, 5, 5, 5);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 2;
		gbc_tabbedPane.gridwidth = 3;
		frmProgrammingContestSystem.getContentPane().add(tabbedPane,
				gbc_tabbedPane);

		submissionsPane = new JScrollPane(new SubmissionView(
				SubmissionView.AS_SERVER, null, submissionModel));
		tabbedPane.addTab("Submissions", null, submissionsPane, null);

		scoreboardPane = new JScrollPane(new ScoreboardView(scoreboardModel));
		tabbedPane.addTab("Scoreboard", null, scoreboardPane, null);

		timeControlPane = new JScrollPane(new TimeControlView(this));
		tabbedPane.addTab("Time Control", null, timeControlPane, null);

		settingsPane = new JScrollPane(new SettingsView());
		tabbedPane.addTab("Settings", null, settingsPane, null);
	}

	/**
	 * Updates the data models for the whole frame.
	 */
	public synchronized void updateViews() {
		this.contestantModel.setContestants(DbAdapter.getAllContestants());
		this.contestantModel.fireTableDataChanged();

		this.languageModel.setLanguages(DbAdapter.getAllLanguages());
		this.languageModel.fireTableDataChanged();

		this.problemModel.setProblems(DbAdapter.getAllProblems());
		this.problemModel.fireTableDataChanged();

		this.submissionModel.setSubmissions(DbAdapter.getAllSubmissions(null));
		this.submissionModel.fireTableDataChanged();

		this.scoreboardModel.setContestants(DbAdapter.getScoreboard());
		this.scoreboardModel.fireTableDataChanged();

		this.timeControlView.updateView();

		lblTimeLeft.setText("Time Left: "
				+ new ContestTime().getDurationBreakdown());// update time left
	}

}