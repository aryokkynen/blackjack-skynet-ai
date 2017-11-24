package ai_blackjack.skynet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

import org.jfree.data.category.DefaultCategoryDataset;

public class OpenFile {
	JFileChooser filechooser = new JFileChooser();
	ArrayList<String> Data = new ArrayList<String>();
	static DefaultTableModel Taulu, Qtaulu;

	List<String> tyot;

	public void FilePicker(DefaultTableModel model, DefaultTableModel model2, DefaultTableModel model3,
			boolean training) throws Exception {
		filechooser.setCurrentDirectory(new java.io.File("./data"));
		if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			Taulu = model2;
			Qtaulu = model3;
			File file = filechooser.getSelectedFile();
			ArrayList<Stock> stockList = new ArrayList<Stock>();

			Scanner input = new Scanner(file);

			while (input.hasNext()) {
				Data.add(input.nextLine());
			}
			input.close();

			for (int i = 1; i < Data.size(); i++) {

				String temp = Data.get(i).toString();

				String[] asdf = temp.split(",");
				String date = asdf[0];
				double price = Double.parseDouble(asdf[1]);
				double adjusted_price = Double.parseDouble(asdf[2]);
				Stock s = new Stock(date, price, adjusted_price, 0, 0,0);
				model.addRow(new Object[] { date, price, adjusted_price });
				stockList.add(s);

			}

			App.startGui(stockList, training);

		}

	}

	public void Trainer(DefaultTableModel model2, boolean training) throws Exception {
		filechooser.setCurrentDirectory(new java.io.File("./data"));
		if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = filechooser.getSelectedFile();
			Taulu = model2;
			ArrayList<Stock> stockList = new ArrayList<Stock>();

			Scanner input = new Scanner(file);

			while (input.hasNext()) {
				Data.add(input.nextLine());
			}
			input.close();

			for (int i = 1; i < Data.size(); i++) {

				String temp = Data.get(i).toString();

				String[] asdf = temp.split(",");
				String date = asdf[0];
				double price = Double.parseDouble(asdf[1]);
				double adjusted_price = Double.parseDouble(asdf[2]);
		
				Stock s = new Stock(date, price, adjusted_price, 0, 0, 0);

				stockList.add(s);

			}

			App.startGui(stockList, training);

		}

	}

	public static void addLine(String string) {
		Taulu.addRow(new Object[] { string });

	}

	public static void addQLine(String state, String qvalue, double old_price, double price, String name, int momemtum,
			String ai_action, String money, int stock_count, String networth, String common_price) {
		Qtaulu.addRow(new Object[] { state, qvalue, old_price, price, name, momemtum, ai_action, money, stock_count,
				networth, common_price });

	}

	public static DefaultCategoryDataset createDataset() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		double networth = 0;
		double stockvalue = 0;
		String input = "";
		String input2 = "";
		double balancer = 0;
		balancer = 10000 / Double.parseDouble(Qtaulu.getValueAt(0, 3).toString());
		for (int i = 0; i < Qtaulu.getRowCount(); i++) {
			input = Qtaulu.getValueAt(i, 9).toString();
			String[] inputArray = input.split(",");
			input2 = Qtaulu.getValueAt(i, 3).toString();
			String[] inputArray2 = input2.split(",");
			networth = Double.parseDouble(inputArray[0]);
			stockvalue = Double.parseDouble(inputArray2[0]) * balancer;
			dataset.addValue(networth, "Networth", Integer.toString(i));
			dataset.addValue(stockvalue, "Stockvalue", Integer.toString(i));
		}

		return dataset;
	}

}
