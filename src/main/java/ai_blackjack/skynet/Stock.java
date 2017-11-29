package ai_blackjack.skynet;

public class Stock {

	String date;
	double share_price;
	double pe_value;
	double adjusted_price; // Includes dividends / split
	int momentum;
	double common_value;

	public Stock(String date, double share_price, double adjusted_price, double pe_value, int momentum,
			double common_value) {
		super();
		this.date = date;
		this.share_price = share_price;
		this.adjusted_price = adjusted_price;
		this.pe_value = pe_value;
		this.momentum = momentum;
		this.common_value = common_value;
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

	public double getAdjusted_price() {
		return adjusted_price;
	}

	public void setAdjusted_price(double adjusted_price) {
		this.adjusted_price = adjusted_price;
	}

	public double getCommon_value() {
		return common_value;
	}

	public void setCommon_value(double common_value) {
		this.common_value = common_value;
	}

	@Override
	public String toString() {
		return "Stock [date=" + date + ", share_price=" + share_price + ", pe_value=" + pe_value + ", adjusted_price="
				+ adjusted_price + ", momentum=" + momentum + ", common_value=" + common_value + "]";
	}

}
