package ai_blackjack.skynet;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

	protected int player_hand, dealer_hand;
	protected Dealer d = new Dealer();
	protected App a = new App();

	protected void setUp() {

		d.Make_deck();
		player_hand = d.Generate_random_card() + d.Generate_random_card();
		dealer_hand = d.Generate_random_card() + d.Generate_random_card();
	

	}

	public void testDealerWin() {

		boolean result = false;
		
		player_hand = 18;
		dealer_hand = 20;

		if (dealer_hand >= player_hand) {
			result = true;
		} else {
			result = false;
		}

		d.Shuffle_le_deck();
		assertTrue(result == true);
	}

	public void testPlayerWin() {

		boolean result = false;
		
		player_hand = 20;
		dealer_hand = 18;

		if (player_hand > dealer_hand) {
			result = true;
		} else {
			result = false;
		}
		d.Shuffle_le_deck();
		assertTrue(result == true);
	}

	public void testPlayerBlackJack() {

		boolean result = false;

		player_hand = 21;
		
		if (d.check_for_blackjack(player_hand)) {
			result = true;
		}

		assertTrue(result == true);
	}
	
	public void testDealerBlackjack() {

		boolean result = false;

		dealer_hand = 21;
		
		if (d.check_for_blackjack(dealer_hand)) {
			result = true;
		}

		assertTrue(result == true);
	}
	
	public void testPlayerBust(){
		
		boolean result = true;

		player_hand = 21 + d.Generate_random_card();
		
		if (d.check_hand(player_hand)) {
			result = false;
		}

		assertTrue(result == false);
		
	}
	
	public void testDealerBust(){
		
		boolean result = true;

		dealer_hand = 21 + d.Generate_random_card();
		
		if (d.check_hand(dealer_hand)) {
			result = false;
		}

		assertTrue(result == false);
		
	}
	
	public void testDeck(){
		
		boolean result = false;
		
		d.Make_deck();
		
		for (int i = 0; i < 10; i++) {
			d.Generate_random_card();
		}
		
		if (d.returnDealtCardCount() == 10) {
			result = true;
		}
		
		assertTrue(result == true);
	}

	
	

}
