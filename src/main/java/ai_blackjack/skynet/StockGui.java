package ai_blackjack.skynet;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StockGui {

	private JFrame frame;
	private JTable table, SecondTable, ThirdTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StockGui window = new StockGui();
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
	public StockGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 461);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane, BorderLayout.NORTH);

		DefaultTableModel model = new DefaultTableModel();
		table = new JTable(model);

		model.addColumn("date");
		model.addColumn("price");
		model.addColumn("pe_value");

		DefaultTableModel model2 = new DefaultTableModel();
		SecondTable = new JTable(model2);

		model2.addColumn("Ai Info");

		DefaultTableModel model3 = new DefaultTableModel();
		ThirdTable = new JTable(model3);

		model3.addColumn("Old price");
		model3.addColumn("Old pe-value");
		model3.addColumn("Price");
		model3.addColumn("Pe-value");
		model3.addColumn("IsWin");
		model3.addColumn("Agent name");
		model3.addColumn("Momemtum");
		model3.addColumn("Ai Action");
		model3.addColumn("Money");
		model3.addColumn("Stock Count");

		JButton btnSelectFile = new JButton("Open file");

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JScrollPane scroll_table = new JScrollPane(table);
		scroll_table.setBounds(5, 10, 300, 150);
		scroll_table.setVisible(true);

		tabbedPane.add("Data", scroll_table);
		frame.setBounds(100, 100, 450, 462);

		JScrollPane scroll_salary_table = new JScrollPane(SecondTable);
		scroll_table.setBounds(5, 10, 300, 150);
		scroll_table.setVisible(true);
		tabbedPane.add("AI data", scroll_salary_table);

		JScrollPane QvalueTAble = new JScrollPane(ThirdTable);
		scroll_table.setBounds(5, 10, 300, 150);
		scroll_table.setVisible(true);
		tabbedPane.add("Q-values", QvalueTAble);

		splitPane.setLeftComponent(btnSelectFile);
		btnSelectFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				OpenFile openfile = new OpenFile();

				try {
					openfile.FilePicker(model, model2, model3);

					frame.setBounds(100, 100, 450, 462);

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});

		JButton btnDoStuffAnd = new JButton("Do stuff and save");
		splitPane.setRightComponent(btnDoStuffAnd);

		btnDoStuffAnd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("lööl");

			}
		});
	}

}
