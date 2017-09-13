package ai_blackjack.skynet;

import java.text.DecimalFormat;

public class App {
	
	public static void main(String[] args) {

		DecimalFormat df = new DecimalFormat("####0.00");
		long start = System.currentTimeMillis();
		
		int dealer_wins = 0;
		int player_wins = 0;

		int games = 5000;

		for (int i = 0; i < games; i++) {

			Dealer d = new Dealer();

			int player_hand;
			int dealer_hand;
			int card1 = d.Generate_random_card();
			int card2 = d.Generate_random_card();
			int player_starting_sum = 12;
			
			int dealer_card1 = d.Generate_random_card();
			int dealer_card2 = d.Generate_random_card();

			int player_card_count = 2;
			int dealer_card_count = 2;			
			
			boolean player_win = false;
			boolean player_stay = false;
			boolean player_bust = false;
			
			boolean dealer_win = false;
			boolean dealer_stay = false;
			boolean dealer_bust = false;

			player_hand = card1 + card2;
			dealer_hand = dealer_card1 + dealer_card2;
			int player_starting_hand = player_hand;
			
			System.out.println("Player starting hand, player card{1} " + card1 + ", player card{2} " + card2 + " total: " + player_hand);
			System.out.println("Dealer starting hand, dealer card{1} " + dealer_card1 + " , dealer card{2} " + dealer_card2 + " total: " + dealer_hand);
			int tolerance = player_starting_sum  + (1 + (int) (Math.random() * 5));
			
			dealer_win = d.check_for_blackjack(dealer_hand);
			


			// Do stuff
			do {
				if (dealer_hand < 17) {
					int dealer_draw = d.Generate_random_card();
					dealer_hand = dealer_hand + dealer_draw;
					dealer_win = d.check_for_blackjack(dealer_hand);
					System.out.println("Dealer draw! " + dealer_draw);
					dealer_card_count++;
					if (dealer_win) {
						System.out.println("Dealer blackjack!");
						break;
					}
				}
				
				// check for dealer bust before continuing
				dealer_bust = d.check_hand(dealer_hand);
				if (!dealer_bust){
					System.out.println("Dealer bust! Over 21. Dealer hand: " + dealer_hand );
					break;
				}
				
				if (player_hand < tolerance || player_starting_hand > tolerance || player_hand < dealer_hand){
					int player_draw = d.Generate_random_card();
					player_hand = dealer_hand + player_draw;
					player_win = d.check_for_blackjack(player_hand);
					System.out.println("Player draw! " + player_draw);
					player_card_count++;
					if (player_win) {
						System.out.println("Player blackjack!");
						break;
					}
				}
				
				// check for dealer bust before continuing
				player_bust = d.check_hand(player_hand);
				if (!player_bust){
					System.out.println("Player bust! Over 21. Player hand: " + player_hand );
					break;
				}
				
				
				if (player_hand >= tolerance && dealer_hand >= 16) {
					break;
				}
						
		
			} while (true); 
			
			
			System.out.println("Random (player) tolerance: " + tolerance);
			System.out.println("Player hand: " + player_hand);
			System.out.println("Dealer hand: " + dealer_hand);			
				
			if (d.check_hand(player_hand) && !dealer_win) {		
								
				if (player_hand > dealer_hand){
					player_win = true;
					player_wins++;
					System.out.println("Player won: PH " + player_hand + " VS DH " + dealer_hand);
				} else {
					player_win = false;
					dealer_wins++;
					System.out.println("Player lost: PH " + player_hand + " VS DH " + dealer_hand);
				}
				
			} else {
				player_win = false;
				dealer_wins++;
				System.out.println("Player lost: PH " + player_hand + " VS DH " + dealer_hand);
			}

			System.out.println("Player cardcount: " + player_card_count);
			System.out.println("Dealer cardcount: " + dealer_card_count + "\n");

		
		//End FOR
		}
		
		
		long end = System.currentTimeMillis();
		System.out.println("****************************************");
		System.out.println("Player wins: " + player_wins);
		System.out.println("Dealer wins: " + dealer_wins);
		System.out.println("Total games: " + games);
		System.out.println("Player win " + df.format(100/((double)games / (double) player_wins)) + "%");
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
