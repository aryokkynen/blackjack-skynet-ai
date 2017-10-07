package ai_blackjack.skynet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	List<Card> cards=new ArrayList<Card>();
	int id=0;
	
	public Deck(){
		for(int i=1;i<5;i++)
		{
			for(int j=1;j<14;j++)
			{
				Card card=new Card(j,i,id);
				this.cards.add(card);
				this.id+=1;
			}
		}
	}
	
	public void shuffleDeck(){
		Collections.shuffle(this.cards);
	}
	
	public Card draw(){
		Card c=null;
		if(!this.cards.isEmpty()){
			c=this.cards.remove(this.cards.size()-1);
		}
		return c;
	}
	
	public boolean returnToDeck(Card card){
		return this.cards.add(card);
	}
	
	public boolean isEmpty(){
		return this.cards.isEmpty();
	}

}
