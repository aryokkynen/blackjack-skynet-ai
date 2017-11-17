package ai_blackjack.skynet;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

public class StockGui {

	private JFrame frame;
	private JTable table, SecondTable, ThirdTable;
	
	int x = 100;
	int y = 100;
	int width = 1200;
	int height = 800;

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
		frame = new JFrame("Skynet stock thing");
		frame.setBounds(x, y, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p = new JPanel();

		frame.getContentPane().add(p, BorderLayout.NORTH);

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
		model3.addColumn("State");
		model3.addColumn("Q-value");
		model3.addColumn("Old price");
		model3.addColumn("Old pe-value");
		model3.addColumn("Price");
		model3.addColumn("Pe-value");
		model3.addColumn("Agent name");
		model3.addColumn("Momemtum");
		model3.addColumn("Ai Action");
		model3.addColumn("Money");
		model3.addColumn("Stock Count");
		model3.addColumn("Networth");
		

		JButton btnSelectFile = new JButton("Open file");

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JScrollPane scroll_table = new JScrollPane(table);
		scroll_table.setBounds(x, y, width, height);
		scroll_table.setVisible(true);

		tabbedPane.add("Imported data", scroll_table);
		frame.setBounds(x, y, width, height);

		JScrollPane ai_data = new JScrollPane(SecondTable);
		scroll_table.setBounds(x, y, width, height);
		scroll_table.setVisible(true);
		tabbedPane.add("AI data", ai_data);

		JScrollPane QvalueTAble = new JScrollPane(ThirdTable);
		scroll_table.setBounds(x, y, width, height);
		scroll_table.setVisible(true);
		tabbedPane.add("Q-values & Misc data", QvalueTAble);

		p.add(btnSelectFile);
		btnSelectFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				OpenFile openfile = new OpenFile();

				try {
					openfile.FilePicker(model, model2, model3);

					JFreeChart lineChart = ChartFactory.createLineChart(
							"Networth", "Date", "Networth",
							OpenFile.createDataset(), PlotOrientation.VERTICAL,
							true, true, false);

					lineChart.getCategoryPlot().getRangeAxis()
							.setLowerBound(7000);

					ChartPanel chartPanel = new ChartPanel(lineChart);
					tabbedPane.add("AI Networth chart", chartPanel);
					chartPanel.setMouseZoomable(true);

					
					
					
					frame.setBounds(x, y, width, height);
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		/*
		 * //JButton btnDoStuffAnd = new JButton("Do stuff and save");
		 * btnDoStuffAnd.setEnabled(false);
		 * splitPane.setRightComponent(btnDoStuffAnd);
		 * 
		 * 
		 * btnDoStuffAnd.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * System.out.println("lööl");
		 * 
		 * } });
		 */
	}

}
