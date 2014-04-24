package view.help;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

import javax.swing.JTextArea;

/**
 * The HowToUseView displays information about how the application can be used.
 */
public class HowToUseView extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HowToUseView frame = new HowToUseView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HowToUseView() {
		setTitle("How to use?");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		scrollPane.setViewportView(textArea);

		// Text
		String text = "When starting up the Programming Contest System for the first time or when it’s not configured, it will open the configuration screen which will ask you to configure the application as a Contestant or as a Contest Admin. As a Contestant, you will need the Contest Admin PC IP address since there is where all the settings and data is stored.\n\n"
				+ "Once configured, the software will create a folder called “pcs” which will store all the data over there including the configuration.\n\n"
				+ "Both the Contestant and Contest Admin have a few things in common, both of them can check the standings and the submissions, except that the Contestant can only see their submissions and not the other contestants.\n\n"
				+ "The Contestant will have the ability to submit a submission to the Contest Admin, and when it is received by the Contest Admin, it will automatically compile and run it and validate it and update the standings.\n\n"
				+ "The Contest Admin will have to add Contestants, Problems, and Languages (Programming Languages) to the system. Contestants will only be able to login if they use the correct credentials from the server. When the Contestant submits, they can only select the problems on the server and only solve with the programming languages specified by the server.\n\n"
				+ "The Contest Admin should set the start and end time for the contest. Since that will restrict Contestants from submitting if the contest is not running yet, and it will also allow the Contest Admin to change the time during the contest.\n\n"
				+ "In the end, the Contestants will be ranked based on the number of problems solved, if there was a tie, they one with the less penalty will be ranked higher. Penalty is the number of wrong submission attempts.\n\n"
				+ "When the contest is over, the Contest Admin has the ability to generate a PDF containing the Contest information and data.";

		// Show on textarea
		textArea.setText(text);
		textArea.setCaretPosition(0);
	}

}