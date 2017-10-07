package ai_blackjack.skynet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shoe {
	int numDecks;
	List<Deck> decks=new ArrayList<Deck>();
    
	public Shoe(int numDecks){
		this.numDecks=numDecks;
		for(int i=0;i<numDecks;i++){
			Deck deck=new Deck();
			this.decks.add(deck);
		}
		
	}
	
	public void shuffleShoe(){
		Collections.shuffle(this.decks);
		for(Deck deck : this.decks){
			deck.shuffleDeck();
		}
	}
	
	public Card draw(){
		for(Deck deck:this.decks){
			if(!deck.isEmpty()){
				return deck.draw();
			}
		}
		return null;
	}
	
	public void returnToShoe(Card card){
		for(Deck deck:this.decks){
			if(deck.cards.size()<52){
				deck.returnToDeck(card);
				break;
			}
		}
	}
	
}
