package view.client;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.Utilities;
import view.help.AboutView;
import view.help.HowToUseView;
import main.Client;

import javax.swing.JMenuItem;

/**
 * This JPanel is the view where the contestant login in.
 */
public class ClientLoginView {

	/**
	 * The client object that called created the JFrame object. It will be used
	 * for sending data back to it once the user logs in.
	 */
	private Client client;

	private JFrame frmProgrammingContestSystem;
	private JTextField txtUsername;
	private JLabel lblContestantLogin;
	private JPasswordField pwdPassword;
	private JButton btnLogin;
	private JMenuBar menuBar;
	private JMenu mnHelp;
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
					ClientLoginView window = new ClientLoginView(null);
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
	public ClientLoginView(Client client) {
		// Store the client object that created this JFrame so that we can send
		// data back to it.
		this.client = client;
		initialize();
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
		frmProgrammingContestSystem.setBounds(100, 100, 450, 196);
		frmProgrammingContestSystem
				.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		frmProgrammingContestSystem.getContentPane().setLayout(gridBagLayout);

		lblContestantLogin = new JLabel("Contestant Login");
		lblContestantLogin.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		GridBagConstraints gbc_lblContestantLogin = new GridBagConstraints();
		gbc_lblContestantLogin.insets = new Insets(5, 5, 5, 5);
		gbc_lblContestantLogin.gridx = 0;
		gbc_lblContestantLogin.gridy = 0;
		gbc_lblContestantLogin.gridwidth = 3;
		frmProgrammingContestSystem.getContentPane().add(lblContestantLogin,
				gbc_lblContestantLogin);

		JLabel lblUsername = new JLabel("Username");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.insets = new Insets(5, 5, 5, 5);
		gbc_lblUsername.gridx = 0;
		gbc_lblUsername.gridy = 1;
		frmProgrammingContestSystem.getContentPane().add(lblUsername,
				gbc_lblUsername);

		txtUsername = new JTextField();
		GridBagConstraints gbc_txtUsername = new GridBagConstraints();
		gbc_txtUsername.insets = new Insets(5, 5, 5, 5);
		gbc_txtUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUsername.gridx = 2;
		gbc_txtUsername.gridy = 1;
		frmProgrammingContestSystem.getContentPane().add(txtUsername,
				gbc_txtUsername);
		txtUsername.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.insets = new Insets(5, 5, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 2;
		frmProgrammingContestSystem.getContentPane().add(lblPassword,
				gbc_lblPassword);

		pwdPassword = new JPasswordField();
		GridBagConstraints gbc_pwdPassword = new GridBagConstraints();
		gbc_pwdPassword.insets = new Insets(5, 5, 5, 5);
		gbc_pwdPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_pwdPassword.gridx = 2;
		gbc_pwdPassword.gridy = 2;
		frmProgrammingContestSystem.getContentPane().add(pwdPassword,
				gbc_pwdPassword);

		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.insets = new Insets(5, 5, 5, 5);
		gbc_btnLogin.gridx = 0;
		gbc_btnLogin.gridy = 3;
		gbc_btnLogin.gridwidth = 3;
		frmProgrammingContestSystem.getContentPane()
				.add(btnLogin, gbc_btnLogin);

		menuBar = new JMenuBar();
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

		mnHelp = new JMenu("Help");
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
	}

	/**
	 * The action that is performed when the login button is clicked.
	 */
	@SuppressWarnings("deprecation")
	public void login() {
		// Send data to the controller, it'll handle the rest.
		client.login(txtUsername.getText(),
				Utilities.MD5(pwdPassword.getText()));
	}

}