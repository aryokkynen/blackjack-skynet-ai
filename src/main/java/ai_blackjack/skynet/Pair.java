package ai_blackjack.skynet;

public class Pair {
	public int[] state;
	public int action;

	public Pair(int[] state, int action) {
		this.state = state;
		this.action = action;
	}

	public int getAction() {
		return this.action;
	}

}