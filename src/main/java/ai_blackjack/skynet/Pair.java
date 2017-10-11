package ai_blackjack.skynet;


public class Pair {
	public int[] state;
	public int action;

	public Pair(int[] state, int action) {
		this.state = state;
		this.action = action;
		
	}
	

	public int getAction() {
		System.out.println(this.action);
		return this.action;
	}
	


}