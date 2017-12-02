package eg.edu.alexu.csd.oop.jdbc.cs73.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PrintResult {

	public JFrame frame2;
	private JLabel[] labels;
	private static String[] staticRows;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrintResult window = new PrintResult(staticRows);
					window.frame2.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PrintResult(String rows[]) {
		staticRows = rows;
		initialize(rows);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String rows[]) {
		frame2 = new JFrame();
		frame2.setResizable(false);
		frame2.setBounds(100, 100, 500,  48 + 63 * (rows.length + 1));
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.getContentPane().setLayout(null);
		labels = new JLabel[rows.length];
		for (int i = 0; i < rows.length; i++) {
			labels[i] = new JLabel(rows[i]);
			labels[i].setHorizontalAlignment(SwingConstants.CENTER);
			labels[i].setFont(new Font("Tahoma", Font.BOLD, 20));
			labels[i].setBounds(12, 13 + i * ( 50 + 13 ), 470, 50);
			frame2.getContentPane().add(labels[i]);
		}
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eg.edu.alexu.csd.oop.jdbc.cs73.view.JDBC_GUI jdbc = new JDBC_GUI();
				jdbc.frame.setVisible(true);
				frame2.setVisible(false);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		if(rows.length > 0){
			btnNewButton.setBounds(187, labels[rows.length - 1].getY() + 63, 120, 50);
		}
		btnNewButton.setBounds(187,  63, 120, 50);
		frame2.getContentPane().add(btnNewButton);
	}
}
