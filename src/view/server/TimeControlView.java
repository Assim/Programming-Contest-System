package view.server;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpinnerDateModel;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JSpinner;
import javax.swing.JButton;

import model.ContestTime;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

/**
 * This JPanel will be used to allow the contest admin to update the contest
 * start and end time.
 */
public class TimeControlView extends JPanel {

	private static final long serialVersionUID = 1L;

	private ServerView serverView;
	private JSpinner spinnerTimeStart;
	private JSpinner spinnerTimeEnd;
	private ContestTime contestTime;
	private JLabel lblTimeIsNot;
	private JLabel lblContestIsOver;
	private JLabel lblContestIsRunning;

	/**
	 * Create the panel.
	 */
	public TimeControlView(ServerView serverView) {
		this.serverView = serverView;
		this.contestTime = new ContestTime();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		lblTimeIsNot = new JLabel("Time is not set yet.");
		lblTimeIsNot.setForeground(Color.RED);
		lblTimeIsNot.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		GridBagConstraints gbc_lblTimeIsNot = new GridBagConstraints();
		gbc_lblTimeIsNot.insets = new Insets(0, 0, 5, 5);
		gbc_lblTimeIsNot.gridx = 0;
		gbc_lblTimeIsNot.gridy = 0;
		add(lblTimeIsNot, gbc_lblTimeIsNot);

		lblContestIsOver = new JLabel("Contest is over.");
		lblContestIsOver.setForeground(Color.RED);
		lblContestIsOver.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		GridBagConstraints gbc_lblContestIsOver = new GridBagConstraints();
		gbc_lblContestIsOver.insets = new Insets(0, 0, 5, 5);
		gbc_lblContestIsOver.gridx = 0;
		gbc_lblContestIsOver.gridy = 1;
		add(lblContestIsOver, gbc_lblContestIsOver);

		lblContestIsRunning = new JLabel("Contest is running.");
		lblContestIsRunning.setForeground(Color.BLUE);
		lblContestIsRunning.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		GridBagConstraints gbc_lblContestIsRunning = new GridBagConstraints();
		gbc_lblContestIsRunning.insets = new Insets(0, 0, 5, 5);
		gbc_lblContestIsRunning.gridx = 0;
		gbc_lblContestIsRunning.gridy = 2;
		add(lblContestIsRunning, gbc_lblContestIsRunning);

		JLabel lblStartTime = new JLabel("Start Time:");
		GridBagConstraints gbc_lblStartTime = new GridBagConstraints();
		gbc_lblStartTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartTime.gridx = 0;
		gbc_lblStartTime.gridy = 3;
		add(lblStartTime, gbc_lblStartTime);

		spinnerTimeStart = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor de_spinnerTimeStart = new JSpinner.DateEditor(
				spinnerTimeStart, "HH:mm");
		spinnerTimeStart.setEditor(de_spinnerTimeStart);
		spinnerTimeStart.setValue(new Date(Long.valueOf(contestTime
				.getTimeStart()))); // will only show the current time
		GridBagConstraints gbc_spinnerTimeStart = new GridBagConstraints();
		gbc_spinnerTimeStart.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerTimeStart.gridx = 3;
		gbc_spinnerTimeStart.gridy = 3;
		add(spinnerTimeStart, gbc_spinnerTimeStart);

		JLabel lblEndTime = new JLabel("End Time:");
		GridBagConstraints gbc_lblEndTime = new GridBagConstraints();
		gbc_lblEndTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblEndTime.gridx = 0;
		gbc_lblEndTime.gridy = 5;
		add(lblEndTime, gbc_lblEndTime);

		spinnerTimeEnd = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor de_spinnerTimeEnd = new JSpinner.DateEditor(
				spinnerTimeEnd, "HH:mm");
		spinnerTimeEnd.setEditor(de_spinnerTimeEnd);
		spinnerTimeEnd
				.setValue(new Date(Long.valueOf(contestTime.getTimeEnd()))); // will
																				// only
																				// show
																				// the
																				// current
																				// time
		GridBagConstraints gbc_spinnerTimeEnd = new GridBagConstraints();
		gbc_spinnerTimeEnd.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerTimeEnd.gridx = 3;
		gbc_spinnerTimeEnd.gridy = 5;
		add(spinnerTimeEnd, gbc_spinnerTimeEnd);

		JButton btnSetContestTime = new JButton("Set Contest Time");
		btnSetContestTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateContestTime();
			}
		});
		GridBagConstraints gbc_btnSetContestTime = new GridBagConstraints();
		gbc_btnSetContestTime.insets = new Insets(0, 0, 0, 5);
		gbc_btnSetContestTime.gridx = 0;
		gbc_btnSetContestTime.gridy = 7;
		add(btnSetContestTime, gbc_btnSetContestTime);

		// Update the view and get the latest data
		updateView();

	}

	/**
	 * This will used when the contest admin sets the contest time and will
	 * automatically store it too.
	 */
	public void updateContestTime() {
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
		sdfTime.setTimeZone(Calendar.getInstance().getTimeZone());

		SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
		sdfDate.setTimeZone(Calendar.getInstance().getTimeZone());

		SimpleDateFormat sdfFullDate = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		sdfFullDate.setTimeZone(Calendar.getInstance().getTimeZone());

		// Today's date
		String dateToday = sdfDate.format(new Date());

		// Get time from spinner
		Date spinnerStart = (Date) spinnerTimeStart.getModel().getValue();
		Date spinnerEnd = (Date) spinnerTimeEnd.getModel().getValue();

		// Convert spinner times to strings
		String timeStart = sdfTime.format(spinnerStart);
		String timeEnd = sdfTime.format(spinnerEnd);

		// Build start datetime and end datetime
		String startDate = dateToday + " " + timeStart;
		String endDate = dateToday + " " + timeEnd;

		// Build full date objects for start and end
		Date start = null;
		Date end = null;
		try {
			start = sdfFullDate.parse(startDate);
			end = sdfFullDate.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long startMilli = start.getTime();
		long endMilli = end.getTime();

		if (startMilli > endMilli) {
			JOptionPane.showMessageDialog(null,
					"Start time can't be after end time.");
			return;
		}

		// Save contest time to the file.
		contestTime.setTimeStart(startMilli);
		contestTime.setTimeEnd(endMilli);

		// Update view
		updateView();

		JOptionPane.showMessageDialog(null, "Time is set.");

		serverView.updateViews(); // update models immediately
	}

	/**
	 * This will update the view and will show the appropriate messages whether
	 * time is set or competititon is running or etc..
	 */
	public synchronized void updateView() {
		// Check if time is not set.
		if (contestTime.getTimeStart() != 0)
			lblTimeIsNot.setVisible(false);
		else
			lblTimeIsNot.setVisible(true);

		// Check if competition is over.
		if (System.currentTimeMillis() < contestTime.getTimeEnd())
			lblContestIsOver.setVisible(false);
		else
			lblContestIsOver.setVisible(true);

		// Check if contest is currently running
		if (!contestTime.canSubmit())
			lblContestIsRunning.setVisible(false);
		else
			lblContestIsRunning.setVisible(true);
	}

}