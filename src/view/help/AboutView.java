package view.help;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;

/**
 * The AboutView display information about the application.
 */
public class AboutView extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AboutView frame = new AboutView();
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
	public AboutView() {
		setTitle("About");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane);

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		scrollPane.setViewportView(textArea);

		// Text
		String text = "The Programming Contest System (PCS) is a BTech Course Project done by Assim Al-Marhuby (Student ID: 16S0940) for the Higher College of Technology, Muscat, Oman.\n\n"
				+ "This project is created using Java (JDK 7) with the Eclipse IDE. The project demonstrates the use of Remote Method Invocation (RMI), Threads, Java Database Connectivity (JDBC) with SQLite, Serialization, and some other Java concepts.";

		// Show on textarea
		textArea.setText(text);
		textArea.setCaretPosition(0);
	}

}