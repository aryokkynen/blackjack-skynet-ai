package ai_blackjack.skynet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;


public class OpenFile {
	JFileChooser filechooser = new JFileChooser();
	ArrayList<String> Data = new ArrayList<String>();
	static DefaultTableModel Taulu, Qtaulu;

	List<String> tyot;
		public void FilePicker(DefaultTableModel model, DefaultTableModel model2, DefaultTableModel model3) throws Exception{
			if(filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				Taulu = model2;
				Qtaulu = model3;
				File file = filechooser.getSelectedFile();
				ArrayList<Stock> stockList = new ArrayList<Stock>();
				
				Scanner input = new Scanner(file);
				
				while(input.hasNext()) {
Data.add(input.nextLine());					
				}
				input.close();

				for (int i = 0; i < Data.size(); i++) {

					String temp = Data.get(i).toString();

					String[] asdf = temp.split(";");
					String date = asdf[0];
					double price = Double.parseDouble(asdf[1]);
					double pe_value = Double.parseDouble(asdf[2]);
					Stock s = new Stock(date, price, pe_value, 0);
					model.addRow(new Object[]{date, price, pe_value});
					stockList.add(s);

				}

				App.doShit(stockList);
			}
			

}
		public static void addLine(String string) {
			Taulu.addRow(new Object[]{string});
			
		}

		public static void addQLine(double old_price, double old_pe_val, double price, double pe_val, boolean isWin,
				String name) {
			Qtaulu.addRow(new Object[]{old_price,old_pe_val,price,pe_val,isWin,name});
			
		}
}
