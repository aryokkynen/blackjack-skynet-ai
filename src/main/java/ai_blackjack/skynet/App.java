package ai_blackjack.skynet;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

	private static final Logger LOGGER = Logger.getLogger(App.class.getName());
	
	// CHANGEABLE VARIABLES
	static int games_to_play = 100;
	static int total_games = 0;
	static int decks = 4;
	static int player_wins = 0;
	static int dealer_wins = 0;
	static DecimalFormat df = new DecimalFormat("####0.00");
	static boolean silent = false;
	

	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		

		/*
		 * Modified version of
		 * https://github.com/XinHuang123/BlackJack-with-Artificial-Intelligence
		 */

		// ALL or OFF
		LOGGER.setLevel(Level.OFF);

		/*
		 * Alpha = Learning rate, set between 0 and 1. Setting it to 0 means
		 * that the Q-values are never updated, hence nothing is learned.
		 * Setting a high value such as 0.9 means that learning can occur
		 * quickly. 
		 * 
		 * Epsilon = Greedy Policy to allow the agent to occasionally
		 * not to take the optimal action according to its experience. 
		 * 
		 * Discount = Gamma, this models the fact that future rewards are worth less than
		 * immediate rewards
		 */

		// Decks || Epsilon || Discount || Alpha || Number of games to play || Agent name
		SkynetAiAgent donkey = new SkynetAiAgent(decks, 0.7, 0.8, 0.9, games_to_play, "donkey");
		train(donkey);

		// Decks || Epsilon || Discount || Alpha || Number of games to play || Agent name
		SkynetAiAgent greedy = new SkynetAiAgent(decks, 0.9, 0.2, 0.9, games_to_play, "GreedySkynet");
		train(greedy);

		exploit(donkey);
		exploit(greedy);
		
		//Do not enable on larger set of games, very slow. Suggested games <10000
		//donkey.printQvalues();
		//greedy.printQvalues();
		
		greedy.saveQvaluesToCSV();
		donkey.saveQvaluesToCSV();

	//	Pair dpairs=(Pair) donkey.qvalues.keySet().toArray()[50];
	//	Pair gpairs=(Pair) greedy.qvalues.keySet().toArray()[50];
		
		long end = System.currentTimeMillis();

		System.out.println("*******************************");
		System.out.println("Skynet wins: " + player_wins);
		System.out.println("Dealer wins: " + dealer_wins);
		System.out.println("Total games: " + total_games);
		System.out.println("Skynet win " + df.format(100 / ((double) total_games / (double) player_wins))+ "%");
		System.out.println("Execution time is " + df.format((end - start) / 1000d) + " seconds");
		System.out.println("*******************************");

	}

	public static void train(SkynetAiAgent agent) {
		long start = System.currentTimeMillis();
		int total = 0;
		int reward = 0;
		int[] oldState;
		int action;
		int games = agent.numTraining;
		while (agent.numTraining > 0) {
			agent.dealer.gameBegin();
			while (true) {
				oldState = agent.getState();
				action = agent.getAction(oldState);
				if (agent.dealer.playerTurn(action)) {
					break;
				}
				int[] newState = agent.getState();
				reward = 0;
				agent.update(oldState, action, newState, reward);
			}
			boolean isWin = agent.dealer.winFlag;
			if (isWin) {
				reward = 100;
				total += 1;
				
			} else {
				reward = -50;
			}
			
			int dealer_hand_count = agent.dealer.dealerHand.size();
			int player_hand_count = agent.dealer.playerHand.size();
			int dealer_hand_value = agent.dealer.getDealerValue();
			int player_hand_value = agent.dealer.getPlayerValue();
			
			agent.info.add(player_hand_count + "#" + player_hand_value + "#" + dealer_hand_count + "#" +dealer_hand_value + "#" + isWin + "#" + agent.getName());
			agent.update(oldState, action, agent.getState(), reward);
			agent.numTraining -= 1;
		}
		if (!silent) {
			long training_end = System.currentTimeMillis();
			System.out.println("*******TRAINING DATA***********");
			System.out.println("Agent: " + agent.getName());
			System.out.println("Won " + total + " out of " + games);
			System.out.println("Training win " + df.format(100 / ((double) games / (double) total)) + "%");
			System.out.println("Training took " + df.format((training_end - start) / 1000d) + " seconds");
			System.out.println("*******TRAINING DATA***********\n");
		}
	}

	public static void exploit(SkynetAiAgent agent) {
		long start = System.currentTimeMillis();
		int total = 0;
		int games = 0;
		int reward = 0;
		int[] oldState;
		int action;
		//Stop greedy and set learning rate to conservative
		agent.setEpsilon(0.01);
		agent.setAlpha(0.2);

		while (games < games_to_play) {
			agent.dealer.gameBegin();
			while (true) {
				oldState = agent.getState();
				action = agent.getAction(oldState);
				if (agent.dealer.playerTurn(action)) {
					break;
				}
				int[] newState = agent.getState();
				reward = 0;
				agent.update(oldState, action, newState, reward);

			}
			boolean isWin = agent.dealer.winFlag;
			if (isWin) {
				reward = 150;
				total += 1;
				player_wins++;
			} else {
				reward = -50;
				dealer_wins++;
			}
			int dealer_hand_count = agent.dealer.dealerHand.size();
			int player_hand_count = agent.dealer.playerHand.size();
			int dealer_hand_value = agent.dealer.getDealerValue();
			int player_hand_value = agent.dealer.getPlayerValue();
			
			agent.info.add(player_hand_count + "#" + player_hand_value + "#" + dealer_hand_count + "#" +dealer_hand_value + "#" + isWin + "#" + agent.getName());
			agent.update(oldState, action, agent.getState(), reward);
			games += 1;
			total_games++;
		}
		if (!silent) {
			long playing_end = System.currentTimeMillis();
			System.out.println("*******AGENT PLAY DATA*********");
			System.out.println("Agent: " + agent.getName());
			System.out.println("Won " + total + " out of " + games);
			System.out.println("Training win " + df.format(100 / ((double) games / (double) total)) + "%");
			System.out.println("Playing took " + df.format((playing_end - start) / 1000d) + " seconds");
			System.out.println("*******AGENT PLAY DATA*********\n");
		}
	}

	@SuppressWarnings("unused")
	public static void play(SkynetAiAgent agent) {

		int total = 0;
		int reward = 0;
		int[] oldState;
		int action;
		int str;
		int games = agent.numTraining;
		Scanner in = new Scanner(System.in);

		while (true) {
			agent.dealer.gameBegin();
			System.out.println("\nNew game begins!\n");
			while (true) {
				agent.dealer.display();
				oldState = agent.getState();
				action = agent.getAction(oldState);
				System.out.println("Suggestion:" + agent.getAction(oldState));
				System.out.println("Please Enter 1 to Hit, 2 to Stand: ");

				str = in.nextInt();

				if (agent.dealer.playerTurn(str)) {
					break;
				}
				int[] newState = agent.getState();
				reward = 0;
				agent.update(oldState, str, newState, reward);
			}
			boolean isWin = agent.dealer.winFlag;
			agent.dealer.display();
			if (isWin) {
				reward = 1;
				total += 1;
				System.out.println("WIN");
			} else {
				reward = -1;
				System.out.println("LOSE");
			}
			agent.dealer.display();
			agent.update(oldState, str, agent.getState(), reward);
			agent.numTraining -= 1;
			in.close();
		}

	}

}
