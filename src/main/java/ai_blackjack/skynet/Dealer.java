package ai_blackjack.skynet;

public class Dealer {
	
	public int Generate_random_card() {

		int minimum = 1;
		int maximum = 11;
		
		int random = minimum + (int)(Math.random() * maximum);
		
		return random;
    }
	
	public int deal (int hand)  {
		
		hand = hand + Generate_random_card();
		
		return hand;
		
	}
	
	
	public String check_hand (int hand)  {
		
		if (hand > 21) {
			return "You lose";
		}
		
		else if (hand == 21) {
			return "BLACKJACK! You win!";
		}
		
		else {
			
			return "Draw more or stay?";
		}
		
	}
	
	public String stay_put(int hand){
		
		if (hand < 22) {
			return "You win! Hand points: " + hand;
		}
		
		return "You lose! Hand points: " + hand;
	}
	
	public boolean check_for_blackjack(int hand){
		
		if (hand == 21){
			return true;
		}
		
		return false;
	}
	

}
