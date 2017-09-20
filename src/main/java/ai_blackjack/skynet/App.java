package ai_blackjack.skynet;

import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;

public class App {

	private static final Logger LOGGER = Logger.getLogger(App.class.getName());
	// CHANGEABLE VARIABLES
	static int min_tolerance = 2;
	static int max_tolerance = 8;
	static int games = 5000000;
	static int player_starting_sum = 14;
	static int dealer_wins = 0;
	static int player_wins = 0;

	public static void main(String[] args) {

		/*
		 * Rules: Decks in shoe = 4 (This can be changed from Dealer.java)
		 * Player draws first, then dealer for starting hand, repeat two times.
		 * Create random game tolerance for player (Starting sum(Set to 21) -
		 * random(1-5)), this is sum which player should not go. This can be
		 * changed by modifying variable min/max tolerance
		 * 
		 * Decks in shoe are shuffled when there is less than <30% cards
		 * remaining
		 * 
		 * Game flow fixed to follow proper blackjack rules.
		 * 
		 * Game flow is following: Player draws card Dealer draws card Player
		 * draws card Dealer draws card Check for blackjack for either player or
		 * dealer Do player actions (Check for bust and blackjack) Do dealer
		 * actions (Check for bust and blackjack) Resolve winner
		 */

		// ALL or OFF
		LOGGER.setLevel(Level.OFF);

		ProgressBar pb = new ProgressBar("Simulating games", games, ProgressBarStyle.ASCII).start();

		DecimalFormat df = new DecimalFormat("####0.00");
		long start = System.currentTimeMillis();

		Dealer d = new Dealer();
		d.Make_deck();

		for (int i = 0; i < games; i++) {

			int player_hand;
			int dealer_hand;
			int player_card1 = d.Generate_random_card();
			int dealer_card1 = d.Generate_random_card();
			int player_card2 = d.Generate_random_card();
			int dealer_card2 = d.Generate_random_card();
			int player_hand_done = 0;
			int dealer_hand_done = 0;

			player_hand = player_card1 + player_card2;
			dealer_hand = dealer_card1 + dealer_card2;

			// Check for starting hand blackjacks
			boolean player_blackjack = d.check_for_blackjack(player_hand);
			boolean dealer_blackjack = d.check_for_blackjack(dealer_hand);

			if (!player_blackjack && !dealer_blackjack) {
				player_hand_done = player_actions(player_hand, d);
				dealer_hand_done = player_actions(dealer_hand, d);
			}
			if (player_blackjack) {
				player_wins++;
				continue;
			} else if (dealer_blackjack) {
				dealer_wins++;
				continue;
			}

			
			boolean is_dealer_hand_bust = d.check_hand(dealer_hand_done);
			boolean is_player_hand_bust = d.check_hand(player_hand_done);
			
			if (is_player_hand_bust) {
				LOGGER.info(LOGGER.getName() + " Player BUST with HAND --> " + player_hand_done + " VS Dealer " + dealer_hand_done + " ROUND: " + (i+1));
				dealer_wins++;
			} else if (is_dealer_hand_bust) {
				LOGGER.info(LOGGER.getName() + " Dealer BUST with HAND --> " + player_hand_done + " VS Dealer " + dealer_hand_done + " ROUND: " + (i+1));
				player_wins++;
			}
			
			if (player_hand_done > dealer_hand_done && !is_dealer_hand_bust && !is_player_hand_bust){
				LOGGER.info(LOGGER.getName() + " Player WIN with HAND --> " + player_hand_done + " VS Dealer " + dealer_hand_done + " ROUND: " + (i+1));
				player_wins++;
			} else if (dealer_hand_done >= player_hand_done &&!is_dealer_hand_bust && !is_player_hand_bust ){
				dealer_wins++;
				LOGGER.info(LOGGER.getName() + " Dealer WIN with HAND --> " + player_hand_done + " VS Dealer " + dealer_hand_done + " ROUND: " + (i+1));
			}
			
			d.Check_shuffle();
			pb.step();
			// End FOR
		}
		pb.stop();
		long end = System.currentTimeMillis();

		System.out.println("\n****************************************");
		System.out.println("Player wins: " + player_wins);
		System.out.println("Dealer wins: " + dealer_wins);
		System.out.println("Total games: " + games);
		System.out.println("Player win " + df.format(100 / ((double) games / (double) player_wins)) + "%");
		System.out.println("Execution time is " + df.format((end - start) / 1000d) + " seconds");
		System.out.println("****************************************");

	}

	public static boolean flip_coin() {
		int flip = 1 + (int) (Math.random() * 2);

		if (flip == 1) {
			return true;
		} else {
			return false;
		}
	}

	public static int player_actions(int player_hand, Dealer d) {
		
		boolean player_bust = false;
		boolean blackjack = false;
		int tolerance = player_starting_sum + (min_tolerance + (int) (Math.random() * max_tolerance));

		do {

			if (player_hand < tolerance) {
				LOGGER.info("player_actions() --> player hand before draw --> " + player_hand);
				int player_draw = d.Generate_random_card();
				player_hand = player_hand + player_draw;
				LOGGER.info("player_actions() --> player draw card --> " + player_draw);

				// check for player bust before continuing
				player_bust = d.check_hand(player_hand);
				if (player_bust) {
					LOGGER.info("player_actions() --> Player BUST --> " + player_hand);
					break;
				}

				// Determine if Ace should be used as "11"
				if (player_draw == 1) {
					if (player_hand <= 12) {
						player_draw = 11;
					}
				}
				blackjack = d.check_for_blackjack(player_hand);
				if (blackjack) {
					LOGGER.info("player_actions() --> Player blackjack --> " + player_hand);
					break;
				}
			} else if (player_hand >= tolerance) {
				break;
			}

			LOGGER.info("player_actions() --> Player hand --> " + player_hand);

		} while (true);

		return player_hand;

	}

	public static int dealer_actions(int dealer_hand, Dealer d) {

		boolean dealer_bust = false;
		boolean blackjack = false;
		
		do {

			if (dealer_hand < 17) {
				LOGGER.info("dealer_actions() --> dealer hand before draw --> " + dealer_hand);
				int dealer_draw = d.Generate_random_card();
				dealer_hand = dealer_hand + dealer_draw;
				LOGGER.info("dealer_actions() --> Dealer draw card --> " + dealer_draw);
				if (dealer_draw == 1) {
					if (dealer_hand <= 12) {
						dealer_hand = 11;
					}
				}

				blackjack = d.check_for_blackjack(dealer_hand);
				if (blackjack) {
					LOGGER.info("dealer_actions() --> Dealer Blackjack --> " + dealer_hand);
					break;
				}

				dealer_bust = d.check_hand(dealer_hand);
				if (dealer_bust) {
					LOGGER.info("dealer_actions() --> Dealer BUST --> " + dealer_hand);
					break;
				}
			}
		} while (true);

		LOGGER.info("dealer_actions() --> Dealer Hand --> " + dealer_hand);
		return dealer_hand;
	}

}
