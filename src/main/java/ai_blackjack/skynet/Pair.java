package ai_blackjack.skynet;

import java.util.Arrays;

public class Pair {
	public int[] state;
	public int action;
	public double[] stateDouble;
	public double action_double;

	public Pair(int[] state, int action) {
		this.state = state;
		this.action = action;
		
	}
	
	public Pair(double[] state, double action_double) {
		this.stateDouble = state;
		this.action_double = action_double;
		
	}

	public int getAction() {
		System.out.println(this.action);
		return this.action;
	}
	
	public double getStockAction(){
		return this.action_double;
	}

	@Override
	public String toString() {
		return "Pair [state=" + Arrays.toString(state) + ", action=" + action + ", stateDouble="
				+ Arrays.toString(stateDouble) + ", action_double=" + action_double + "]";
	}

	

}