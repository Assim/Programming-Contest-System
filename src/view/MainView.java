package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTextField;

import main.Application;
import util.ConfigManager;
import util.DbAdapter;
import view.help.AboutView;
import view.help.HowToUseView;

import javax.swing.JMenuItem;

/**
 * This is the main view that shows up if the application isn't configured yet
 * to be a client (contestant) or server (contest admin). It will allow the user
 * to configure so that next time it'll start up the actual role
 * (client/server).
 */
public class MainView {

	private JFrame frmProgrammingContestSystem;
	private JTextField txtContestAdminIp;
	private JLabel lblIpVerification;
	private JLabel lblContestAdminIp;
	private JButton btnConfigure;
	private JComboBox<String> comboBox;
	private JMenuItem mntmAbout;
	private JMenuItem mntmHowToUse;
	private JMenu mnFile;
	private JMenuItem mntmExit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainView window = new MainView();
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
	public MainView() {
		initialize();
	}

	/**
	 * Show frame
	 */
	public void show() {
		MainView window = new MainView();
		window.frmProgrammingContestSystem.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmProgrammingContestSystem = new JFrame();
		frmProgrammingContestSystem.setTitle("Programming Contest System");
		frmProgrammingContestSystem.setBounds(100, 100, 450, 249);
		frmProgrammingContestSystem
				.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
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
		gbc_lblProgrammingContestSystem.gridwidth = 4;
		frmProgrammingContestSystem.getContentPane().add(
				lblProgrammingContestSystem, gbc_lblProgrammingContestSystem);

		JLabel lblConfigureThisComputer = new JLabel("Configure This Computer");
		GridBagConstraints gbc_lblConfigureThisComputer = new GridBagConstraints();
		gbc_lblConfigureThisComputer.insets = new Insets(5, 5, 5, 0);
		gbc_lblConfigureThisComputer.gridx = 0;
		gbc_lblConfigureThisComputer.gridy = 1;
		gbc_lblConfigureThisComputer.gridwidth = 4;
		frmProgrammingContestSystem.getContentPane().add(
				lblConfigureThisComputer, gbc_lblConfigureThisComputer);

		JLabel lblRole = new JLabel("Role");
		GridBagConstraints gbc_lblRole = new GridBagConstraints();
		gbc_lblRole.insets = new Insets(5, 5, 5, 5);
		gbc_lblRole.gridx = 0;
		gbc_lblRole.gridy = 2;
		frmProgrammingContestSystem.getContentPane().add(lblRole, gbc_lblRole);

		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex() == 0) {
					txtContestAdminIp.setEnabled(true);
					lblContestAdminIp.setEnabled(true);
				} else {
					txtContestAdminIp.setText("");
					txtContestAdminIp.setEnabled(false);
					lblContestAdminIp.setEnabled(false);
					lblIpVerification.setText("");
				}
				updateConfigureButtonState();
			}
		});
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Contestant", "Contest Admin" }));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(5, 5, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 2;
		gbc_comboBox.gridwidth = 2;
		frmProgrammingContestSystem.getContentPane()
				.add(comboBox, gbc_comboBox);

		lblContestAdminIp = new JLabel("Contest Admin IP");
		GridBagConstraints gbc_lblContestAdminIp = new GridBagConstraints();
		gbc_lblContestAdminIp.anchor = GridBagConstraints.EAST;
		gbc_lblContestAdminIp.insets = new Insets(5, 5, 5, 5);
		gbc_lblContestAdminIp.gridx = 0;
		gbc_lblContestAdminIp.gridy = 3;
		frmProgrammingContestSystem.getContentPane().add(lblContestAdminIp,
				gbc_lblContestAdminIp);

		txtContestAdminIp = new JTextField();
		txtContestAdminIp.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String exp = "\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
						+ "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
						+ "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
						+ "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b";
				if (txtContestAdminIp.getText().matches(exp)) {
					lblIpVerification.setForeground(Color.green);
					lblIpVerification.setText("Valid IP Address");
				} else {
					lblIpVerification.setForeground(Color.red);
					lblIpVerification.setText("Invalid IP Address");
				}
				updateConfigureButtonState();
			}
		});
		GridBagConstraints gbc_txtContestAdminIp = new GridBagConstraints();
		gbc_txtContestAdminIp.insets = new Insets(5, 5, 5, 5);
		gbc_txtContestAdminIp.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtContestAdminIp.gridx = 1;
		gbc_txtContestAdminIp.gridy = 3;
		frmProgrammingContestSystem.getContentPane().add(txtContestAdminIp,
				gbc_txtContestAdminIp);
		txtContestAdminIp.setColumns(8);

		btnConfigure = new JButton("Configure");
		btnConfigure.setEnabled(false);
		btnConfigure.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				configure();
			}
		});

		lblIpVerification = new JLabel("");
		GridBagConstraints gbc_lblIpVerification = new GridBagConstraints();
		gbc_lblIpVerification.insets = new Insets(0, 0, 5, 5);
		gbc_lblIpVerification.gridx = 2;
		gbc_lblIpVerification.gridy = 3;
		frmProgrammingContestSystem.getContentPane().add(lblIpVerification,
				gbc_lblIpVerification);
		GridBagConstraints gbc_btnConfigure = new GridBagConstraints();
		gbc_btnConfigure.insets = new Insets(5, 5, 0, 0);
		gbc_btnConfigure.gridx = 0;
		gbc_btnConfigure.gridy = 5;
		gbc_btnConfigure.gridwidth = 4;
		frmProgrammingContestSystem.getContentPane().add(btnConfigure,
				gbc_btnConfigure);

		JMenuBar menuBar = new JMenuBar();
		frmProgrammingContestSystem.setJMenuBar(menuBar);

		mnFile = new JMenu("File");
		menuBar.add(mnFile);

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
	}

	/**
	 * Update configure button state based on form field values.
	 */
	public void updateConfigureButtonState() {
		if (comboBox.getModel().getSelectedItem().equals("Contestant")) {
			if (lblIpVerification.getText().equals("Valid IP Address"))
				btnConfigure.setEnabled(true);
			else
				btnConfigure.setEnabled(false);
		} else
			btnConfigure.setEnabled(true);
	}

	/**
	 * Event when configure button is clicked.
	 */
	public void configure() {
		// Make a pcs directory for all the files
		File directory = new File("pcs");
		directory.mkdir();

		// Create the configuration file
		ConfigManager cm = new ConfigManager();
		if (comboBox.getModel().getSelectedItem().equals("Contestant")) {
			cm.setRole(ConfigManager.ROLE_CLIENT);
			cm.setServerIp(txtContestAdminIp.getText());
		} else {
			cm.setRole(ConfigManager.ROLE_SERVER);

			// Create database if doesn't exist (for server only)
			DbAdapter.createTables();
		}

		this.frmProgrammingContestSystem.dispose();
		Application.main(null); // Re-run main so that it'll show the correct
								// view.
	}

}