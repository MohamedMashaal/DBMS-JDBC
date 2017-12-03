package eg.edu.alexu.csd.oop.jdbc.cs73.view;

import java.awt.Component;

import javax.swing.table.DefaultTableModel;

public class JdbcView {
	private javax.swing.JFrame mainWindow;
	private javax.swing.JList<String> history;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton pathButton;
    private javax.swing.JLabel pathLabel;
    private javax.swing.JButton queryButton;
    private javax.swing.JTextField queryField;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTable table;
    private javax.swing.JTextArea textArea;
    private javax.swing.JLabel titleLabel;

	public JdbcView() {
		initElements();
		setupView();
	}

	private void initElements() {
		mainWindow = new javax.swing.JFrame("JDBC Tester");
		titleLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        history = new javax.swing.JList<>();
        queryField = new javax.swing.JTextField();
        queryButton = new javax.swing.JButton();
        pathLabel = new javax.swing.JLabel();
        pathButton = new javax.swing.JButton();
        tabbedPane = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
	}

	private void setupView() {
        mainWindow.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("JDBC Tester");

        jScrollPane1.setViewportView(history);

        queryField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        queryField.setText("Enter your query");
        queryField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        queryButton.setText("Query");

        pathLabel.setBackground(new java.awt.Color(255, 255, 255));
        pathLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pathLabel.setText("current path");
        pathLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pathLabel.setOpaque(true);

        pathButton.setText("PATH");

        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane2.setViewportView(textArea);

        tabbedPane.addTab("Text Area", jScrollPane2);
        jScrollPane3.setViewportView(table);

        tabbedPane.addTab("Table", jScrollPane3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(mainWindow.getContentPane());
        mainWindow.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabbedPane)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(queryField)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(queryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(pathButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(pathLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 251, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(queryField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(queryButton)
                        .addGap(1, 1, 1)
                        .addComponent(pathLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pathButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainWindow.pack();
		mainWindow.setVisible(true);
	}
	
	public void setPathLabel(String path) {
		pathLabel.setText(path);
	}

	public Component getMainWindow() {
		return mainWindow;
	}

	public void clear() {
		textArea.setText("");
		history.setListData(new String [0]);
		table.setModel(new DefaultTableModel( new String[0][] , new String[0]));
	}
	
	public javax.swing.JButton getPathButton() {
		return pathButton;
	}

	public javax.swing.JList<String> getHistory() {
		return history;
	}

	public javax.swing.JLabel getPathLabel() {
		return pathLabel;
	}

	public javax.swing.JButton getQueryButton() {
		return queryButton;
	}

	public javax.swing.JTextField getQueryField() {
		return queryField;
	}

	public javax.swing.JTable getTable() {
		return table;
	}

	public javax.swing.JTextArea getTextArea() {
		return textArea;
	}
	
}
