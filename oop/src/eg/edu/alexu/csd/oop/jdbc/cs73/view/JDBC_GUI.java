package eg.edu.alexu.csd.oop.jdbc.cs73.view;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JDBC_GUI {

	public JFrame frame;
	private JTextField sqlStatement;
	private JList<String> sqlHistory = new JList<String>();
	private DefaultListModel<String> historyModel = new DefaultListModel<>();
	private eg.edu.alexu.csd.oop.jdbc.cs73.view.Controller control = new Controller();
	private String[] rows;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JDBC_GUI window = new JDBC_GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JDBC_GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(50, 50, 500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		sqlStatement = new JTextField();
		sqlStatement.setFont(new Font("Tahoma", Font.BOLD, 20));
		sqlStatement.setHorizontalAlignment(SwingConstants.CENTER);
		sqlStatement.setBounds(14, 212, 470, 50);
		frame.getContentPane().add(sqlStatement);
		sqlStatement.setColumns(10);
		JButton ok = new JButton("RUN SQL");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!sqlStatement.getText().equals("")) {
					rows = control.getResult(sqlStatement.getText());
					PrintResult pr = new PrintResult(rows);
					pr.frame2.setVisible(true);
					frame.setVisible(false);
					historyModel.addElement(sqlStatement.getText());
					sqlHistory.setModel(historyModel);
				}
			}
		});
		ok.setFont(new Font("Tahoma", Font.BOLD, 18));
		ok.setBounds(189, 277, 120, 60);
		frame.getContentPane().add(ok);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 50, 230, 149);
		frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(sqlHistory);
		sqlHistory.setBackground(Color.WHITE);
		JLabel lblNewLabel = new JLabel("History");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(14, 13, 230, 24);
		frame.getContentPane().add(lblNewLabel);
	}
}
