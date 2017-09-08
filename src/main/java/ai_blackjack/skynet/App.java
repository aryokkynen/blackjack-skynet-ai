package ai_blackjack.skynet;

import java.text.DecimalFormat;

public class App {
	
	public static void main(String[] args) {

		DecimalFormat df = new DecimalFormat("####0.00");
		long start = System.currentTimeMillis();
		
		int losses = 0;
		int wins = 0;

		int games = 5000;

		for (int i = 0; i < games; i++) {

			Dealer d = new Dealer();

			int starting_hand;

			int card1 = d.Generate_random_card();
			int card2 = d.Generate_random_card();

			int cardcount = 2;
			
			boolean hand_status;

			boolean win = false;
			boolean stay = false;

			starting_hand = card1 + card2;

			System.out.println("Starting hand, card[1] " + card1 + " card[2] " + card2 + " total: " + starting_hand);

			win = d.check_for_blackjack(starting_hand);

			if (win) {
				System.out.println("Blackjack!");
			} else {
				
				//Randomly decide if we want new card
				boolean coin = flip_coin();
				
				if (coin && !win) {
					starting_hand = d.deal(starting_hand);
					cardcount++;
					System.out.println("RANDOMLY SELECTED TO DRAW MORE CARDS, CURRENT HAND" + starting_hand);
					hand_status = d.check_hand(starting_hand);
					if (!hand_status) {
						System.out.println("YOU LOOSE");
						win = false;
					}
					
				} else  {
					System.out.println("RANDOMLY SELECTED TO STAY");
					hand_status = d.check_hand(starting_hand);
					stay = true;
					if (!hand_status) {
						System.out.println("YOU LOOSE");
						win = false;
					}
				}
					
			}

			// Randomly decide if more cards are needed		
			
			boolean coin = flip_coin();
			
			if (coin && !win && !stay) {
				starting_hand = d.deal(starting_hand);
				System.out.println("RANDOMLY SELECTED TO DRAW MORE CARDS, CURRENT HAND" + starting_hand);
				cardcount++;
				hand_status = d.check_hand(starting_hand);
				if (!hand_status) {
					System.out.println("YOU LOOSE");
					win = false;
				}
				
		
			}	
				
			if (d.check_hand(starting_hand)) {				
				win = true;
				wins++;
				System.out.println("Won " + starting_hand);
			} else {
				win = false;
				losses++;
				System.out.println("Lost " + starting_hand);
			}

			System.out.println("Cardcount: " + cardcount + "\n");
		
		
		
		
		
		
		
		
		//End FOR
		}
		
		
		long end = System.currentTimeMillis();
		System.out.println("****************************************");
		System.out.println("Wins: " + wins);
		System.out.println("Losses: " + losses);
		System.out.println("Total games: " + games);
		System.out.println("Win " + df.format(100/((double)games / (double) wins)) + "%");
		System.out.println("Execution time is " + df.format((end - start) / 1000d) + " seconds");
		System.out.println("****************************************");
		
	}
	
	public static boolean flip_coin(){
		int flip = 1 + (int) (Math.random() * 2);		
		
		if (flip == 1) {
			return true;
		} 
		else {
			return false;
		}
	}
	
}
