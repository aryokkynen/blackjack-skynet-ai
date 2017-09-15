package ai_blackjack.skynet;

import java.util.ArrayList;
import java.util.List;

public class Dealer {

	List<Integer> deckList = new ArrayList<Integer>();
	int decks = 4;
	int dealt = 0;

	public void Make_deck() {
		deckList.add(1);
		deckList.add(1);
		deckList.add(1);
		deckList.add(1);
		deckList.add(1);
		deckList.add(1);
		deckList.add(1);
		deckList.add(1);
		deckList.add(1);
		deckList.add(1);
		deckList.add(1);
		Shuffle_le_deck();
	}

	public int Generate_random_card() {
		boolean dublicate = true;
		int random = 0;

		while (dublicate == true) {

			int minimum = 1;
			int maximum = 11;

			random = minimum + (int) (Math.random() * maximum);

			int card = random - 1;
			if (deckList.get(card) != 0) {
				deckList.set(card, deckList.get(card) - 1);
				dealt = dealt + 1;
				// System.out.println("Dealt a card " + random);
				dublicate = false;
			} else
				dublicate = true;
		}

		return random;
	}

	public void Check_shuffle() {
		//System.out.println((((double) dealt) / (52 * (double) decks)));
		if ((((double) dealt) / (52 * (double) decks)) > 0.7) {
			Shuffle_le_deck();
		}
	}

	public void Shuffle_le_deck() {
		//System.out.println("Shuffling le deck. Bitches!");
		dealt = 0;
		deckList.set(0, 4 * decks);
		deckList.set(1, 4 * decks);
		deckList.set(2, 4 * decks);
		deckList.set(3, 4 * decks);
		deckList.set(4, 4 * decks);
		deckList.set(5, 4 * decks);
		deckList.set(6, 4 * decks);
		deckList.set(7, 4 * decks);
		deckList.set(8, 4 * decks);
		deckList.set(9, 4 * decks);
		deckList.set(10, 12 * decks);
	}

	public int deal(int hand) {

		hand = hand + Generate_random_card();

		return hand;

	}

	public boolean check_hand(int hand) {

		if (hand > 21) {
			return false;
		} else {
			return true;
		}

	}

	public boolean check_for_blackjack(int hand) {

		if (hand == 21) {
			return true;
		}

		return false;
	}

}
