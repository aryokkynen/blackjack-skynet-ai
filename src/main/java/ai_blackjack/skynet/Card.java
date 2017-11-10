package ai_blackjack.skynet;

public class Card {
	int rank; // 1~13
	int suit; // 1~4
	int id;

	public Card(int rank, int suit, int id) {
		this.rank = rank;
		this.suit = suit;
		this.id = id;
	}

}
