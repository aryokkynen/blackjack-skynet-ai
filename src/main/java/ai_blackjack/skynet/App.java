package ai_blackjack.skynet;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

	private static final Logger LOGGER = Logger.getLogger(App.class.getName());
	
	// CHANGEABLE VARIABLES
	static int games_to_train = 50000;
	static int games_to_exploit = 50000;
	static int games_to_play_with_money = 500000;
	static int decks = 1;
	static double agent_money = 1000;
	static double default_bet = 10;
	// DO NOT CHANGE THESE
	static int total_games = 0;
	static int player_wins = 0;
	static int dealer_wins = 0;
	static DecimalFormat df = new DecimalFormat("####0.00");
	static boolean silent = false;
	static double best_win = 0;
	static SkynetAiAgent best_agent;

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
		//SkynetAiAgent donkey = new SkynetAiAgent(decks, 0.4, 0.2, 0.5, games_to_train, "donkey", agent_money);
		//train(donkey);
		//exploit(donkey);
		//playWithMoney(donkey, default_bet);
		// Decks || Epsilon || Discount || Alpha || Number of games to play || Agent name
		SkynetAiAgent greedy = new SkynetAiAgent(decks, 0.9, 0.2, 0.9, games_to_train, "GreedySkynet", agent_money);
		train(greedy);
		exploit(greedy);
		playWithMoney(greedy, default_bet);
		
		
		// Batch testing of agents
		/*
		for (int i = 1; i < 11; i++) {
			String name = "donkey"+i;
			SkynetAiAgent q = new SkynetAiAgent(decks, (i*0.1), (0.1), (0.1), games_to_train, name, agent_money);
			SkynetAiAgent q2 = new SkynetAiAgent(decks, (0.1), (i*0.1), (0.1), games_to_train, name + "q2", agent_money);
			SkynetAiAgent q3 = new SkynetAiAgent(decks, (0.1), (0.1), (i*0.1), games_to_train, name + "q3", agent_money);
			SkynetAiAgent q4 = new SkynetAiAgent(decks, (i*0.1), (i*0.1), (0.1), games_to_train, name + "q4", agent_money);
			SkynetAiAgent q5 = new SkynetAiAgent(decks, (0.1), (i*0.1), (i*0.1), games_to_train, name + "q5", agent_money);
			SkynetAiAgent q6 = new SkynetAiAgent(decks, (i*0.1), (0.1), (i*0.1), games_to_train, name + "q6", agent_money);
			SkynetAiAgent q7= new SkynetAiAgent(decks, (i*0.1), (i*0.1), (i*0.1), games_to_train, name + "q7", agent_money);
			
			train(q);
			exploit(q);
			train(q2);
			exploit(q2);
			train(q3);
			exploit(q3);
			train(q4);
			exploit(q4);
			train(q5);
			exploit(q5);
			train(q6);
			exploit(q6);
			train(q7);
			exploit(q7);
			playWithMoney(q, default_bet);
			playWithMoney(q2, default_bet);
			playWithMoney(q3, default_bet);
			playWithMoney(q4, default_bet);
			playWithMoney(q5, default_bet);
			playWithMoney(q6, default_bet);
			playWithMoney(q7, default_bet);
			
			//q.saveQvaluesToCSV();
		}
		*/
		
		//Do not enable on larger set of games, very slow. Suggested games <10000
		//donkey.printQvalues();
		//greedy.printQvalues();
		
		//greedy.saveQvaluesToCSV();
		//donkey.saveQvaluesToCSV();

		//Pair dpairs=(Pair) donkey.qvalues.keySet().toArray()[50];
		//Pair gpairs=(Pair) greedy.qvalues.keySet().toArray()[50];
		
		long end = System.currentTimeMillis();
		System.out.println("*******************************");
		System.out.println("Best agent, win " + df.format(best_win) + "% Name: " + best_agent.getName());
		System.out.println("Best agent, balance: " + df.format(best_agent.getMoney()));
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
				reward = 1;
				total += 1;
				
			} else {
				reward = -1;
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
			System.out.println("Agent specs: A:" + agent.getAlpha() + " D: " + agent.getDiscount() + " E: " + agent.epsilon);
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
		agent.setEpsilon(0);
		agent.setAlpha(0.2);

		while (games < games_to_exploit) {
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
				reward = 2;
				total += 1;
			} else {
				reward = -1;
			}
			
			//agent.info.add(player_hand_count + "#" + player_hand_value + "#" + dealer_hand_count + "#" +dealer_hand_value + "#" + isWin + "#" + agent.getName());
			agent.update(oldState, action, agent.getState(), reward);
			games += 1;
			
		}
		if (!silent) {
			long playing_end = System.currentTimeMillis();			
			System.out.println("*******AGENT EXPLOIT DATA*********");
			System.out.println("Agent: " + agent.getName());
			System.out.println("Agent specs: A:" + agent.getAlpha() + " D: " + agent.getDiscount() + " E: " + agent.epsilon);
			System.out.println("Won " + total + " out of " + games);
			System.out.println("Playing win " + df.format(100 / ((double) games / (double) total)) + "%");
			System.out.println("Playing took " + df.format((playing_end - start) / 1000d) + " seconds");
			System.out.println("*******AGENT EXPLOIT DATA*********\n");

		}
	}

	
	public static void playWithMoney(SkynetAiAgent agent, double bet) {
		long start = System.currentTimeMillis();
		int total = 0;
		int reward = 0;
		int[] oldState;
		int action;
		int games = agent.numTraining;
		
		//Stop learning, use stuff you have. Update rewards on win/loss still.
		agent.setEpsilon(0);
		agent.setAlpha(0);
		agent.setDiscount(0);
		

		while (games < games_to_play_with_money) {
			agent.dealer.gameBegin();
			agent.setMoney(agent.money-bet);
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
				reward = 1;
				total += 1;
				player_wins++;
				if (agent.dealer.getPlayerValue() == 21 && agent.dealer.getPlayerHand().size() == 2){
					agent.setMoney(agent.money = agent.money + bet*5);
					//System.out.println("Jackpot with two cards, won: "+ (bet*5));
					
				}
				
				else if (agent.dealer.getPlayerValue() == 21) {
					agent.setMoney(agent.money = (agent.money + bet*3.5));
					//System.out.println("Jackpot, won: "+ bet*3.5);
					
				}
				else {
					agent.setMoney(agent.money = (agent.money + bet*2));
					//System.out.println("Won: "+ (bet*2));
				}

				
			} else {
				reward = -1;
				dealer_wins++;
			}
			//System.out.println("Agent money: " + agent.money);
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
			System.out.println("Agent specs: A:" + agent.getAlpha() + " D: " + agent.getDiscount() + " E: " + agent.epsilon);
			System.out.println("Won " + total + " out of " + games);
			System.out.println("Playing win " + df.format(100 / ((double) games / (double) total)) + "%");
			System.out.println("Playing took " + df.format((playing_end - start) / 1000d) + " seconds");
			System.out.println("Balance left: " + agent.money);
			System.out.println("*******AGENT PLAY DATA*********\n");
		}
		double win = 100 / ((double) games / (double) total);
		if (win > best_win){
			best_win = win;
			best_agent = agent;
		}

	}

}
