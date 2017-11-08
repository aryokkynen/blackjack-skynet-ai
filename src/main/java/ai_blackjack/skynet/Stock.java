package ai_blackjack.skynet;

public class Stock {
	
	String date;
	double share_price;
	double pe_value;
	int momentum;


	public Stock(String date, double share_price, double pe_value, int momentum) {
		super();
		this.date = date;
		this.share_price = share_price;
		this.pe_value = pe_value;
		this.momentum = momentum;
	}

	public Stock() {
		super();
		
	}

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getShare_price() {
		return share_price;
	}
	public void setShare_price(double share_price) {
		this.share_price = share_price;
	}
	public double getPe_value() {
		return pe_value;
	}
	public void setPe_value(double pe_value) {
		this.pe_value = pe_value;
	}
	
	public int getMomentum() {
		return momentum;
	}
	public void setMomentum(int momentum) {
		this.momentum = momentum;
	}
	
	@Override
	public String toString() {
		return "Stock [date=" + date + ", share_price=" + share_price + ", pe_value=" + pe_value + ", momentum="
				+ momentum + "]";
	}
	
	

}
