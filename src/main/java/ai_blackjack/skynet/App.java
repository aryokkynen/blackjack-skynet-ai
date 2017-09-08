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

			boolean win = false;

			starting_hand = card1 + card2;

			System.out.println("Starting hand, card1 " + card1 + " card2: " + card2 + " total: " + starting_hand);

			win = d.check_for_blackjack(starting_hand);

			if (win) {
				System.out.println("Blackjack!");

			} else {
				starting_hand = d.deal(starting_hand);
			}

			System.out.println("After first card draw: " + starting_hand);

			String status = d.check_hand(starting_hand);

			if (status.equals("Draw more or stay?") && !win) {

				int random = 1 + (int) (Math.random() * 2);

				if (random == 2) {
					starting_hand = starting_hand + d.Generate_random_card();
				}
				System.out.println("RANDOM SELECTED STAY, CURRENT HAND VALUE " + starting_hand);
				System.out.println("CHECKING FOR WIN/LOSE");
				System.out.println(d.stay_put(starting_hand));
			}

			if (d.stay_put(starting_hand).contains("You win!")) {
				win = true;
			} else {
				win = false;
			}

			if (win) {
				wins++;
			} else {
				losses++;
			}

		}
		
		
		
		long end = System.currentTimeMillis();
		System.out.println("****************************************");
		System.out.println("Wins: " + wins);
		System.out.println("Losses: " + losses);
		System.out.println("Total games: " + games);
		System.out.println("Win " + df.format(((double) games / wins - 1) * 100) + "%");
		System.out.println("Execution time is " + df.format((end - start) / 1000d) + " seconds");
		System.out.println("****************************************");
	}
}
