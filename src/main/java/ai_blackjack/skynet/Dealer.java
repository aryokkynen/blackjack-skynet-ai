package ai_blackjack.skynet;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dealer {
	int numDecks;
	Shoe shoe;
	List<Card> playerHand=new ArrayList<Card>();
	List<Card> dealerHand=new ArrayList<Card>();
	int playerValue=0;
	boolean playerAceFlag=false;
	int dealerValue=0;
	boolean dealerAceFlag=false;
	public boolean winFlag=false;
	public boolean isSilent;
	List<Card> discardedCards=new ArrayList<Card>();
	boolean doneFlag=false;
	
	public Dealer(int numDecks,boolean isSilent){
		this.numDecks=numDecks;
		this.isSilent=isSilent;
		this.shoe=new Shoe(numDecks);
		this.shoe.shuffleShoe();
	}
	
	public boolean gameBegin(){
		this.winFlag=false;
		this.doneFlag=false;
		this.playerHand=new ArrayList<Card>();
		this.dealerHand=new ArrayList<Card>();
		
		while(true){
			this.deal();
			this.handValue();
			if(!this.isSilent)				 
				this.playerTurn(2);
			else
				return true;
			System.out.print("Another Game ? 1:Yes,2:No:");
			Scanner in = new Scanner(System.in);			
			int str=in.nextInt();
			in.close();
			if(str==2)
				break;
		}
		return false;
	}

	public boolean playerTurn(int action) {
		this.handValue();
		
		if(this.playerValue > 21){
			if(this.isSilent){
				return this.gameEndSilent();
			}
			else{
				return this.gameEnd();
			}
		}
		
		if(this.playerValue == 21){
			if(this.isSilent){
				return this.gameEndSilent();
			}
			else{
				return this.gameEnd();
			}
		}
		
		if(this.playerAceFlag){
			if(this.isSilent){
				if(this.playerValue + 10 ==21){
					return this.gameEndSilent();
				}
			}
			else{
				if(this.playerValue + 10 == 21){
					this.playerValue +=10;
					this.playerAceFlag =false;
					return this.gameEnd();
				}
			}
		}
		
		while(this.playerValue < 22){
			if(this.isSilent){
				if(action==2){
					return this.dealerTurn();
				}
				else{
					this.playerHand.add(this.drawFromShoe());
					this.handValue();
					if(this.playerValue > 21){
						return this.gameEndSilent();
					}
					if(this.playerValue == 21){
						return this.gameEndSilent();
					}
					if(this.playerAceFlag){
						if(this.playerValue + 10 ==21){
							return this.gameEndSilent();
						}
					}
					return false;
				}
			}
			else{
				this.display();
				System.out.print("Please Enter 1 to Hit, 2 to Stand: ");
				Scanner in = new Scanner(System.in);			
				int str=in.nextInt();
				in.close();
				if(str==2){
					this.dealerTurn();
					return true;
				}
				this.playerHand.add(this.drawFromShoe());
				this.handValue();
				
				if(this.playerValue > 21){
					this.gameEnd();
					return true;
				}
				if(this.playerValue ==21){
					this.dealerTurn();
					return true;
				}
				if(this.playerAceFlag){
					if(this.playerValue + 10 ==21){
						this.playerValue +=10;
						this.playerAceFlag = false;
						this.dealerTurn();
						return true;
					}
				}
			}
		}
		return true;
	}
	
	@SuppressWarnings("null")
	public boolean dealerTurn(){
		boolean highAce=false;
		this.dealerHand.add(this.drawFromShoe());
		this.handValue();
		
		if(this.dealerValue>21){
			if(this.isSilent){
				return this.gameEndSilent();
			}
			else{
				return this.gameEnd();
			}
		}
		if(this.dealerValue>16){
			if(this.isSilent){
				return this.gameEndSilent();
			}
			else{
				return this.gameEnd();
			}
		}
		while(this.dealerValue<17){
			this.dealerHand.add(this.drawFromShoe());
			this.handValue();
			if(this.dealerAceFlag){
				if(highAce){
					if(this.dealerValue==21){
						if(this.isSilent){
							return this.gameEndSilent();
						}
						else{
							this.dealerAceFlag=false;
							return this.gameEnd();
						}
					}
					if(this.dealerValue > 21){
						highAce = false;
						this.dealerValue -=10;
					}
					
				}
				if(this.dealerValue+10<22){
					highAce=true;
					this.dealerValue += 10;
				}
			}
			
			if(this.dealerValue>21){
				if(this.isSilent){
					return this.gameEndSilent();
				}
				else{
					return this.gameEnd();
				}
			}
			
			if(this.dealerValue>16){
				if(highAce){
					this.dealerValue -=10;
				}
				else{
					if(this.isSilent){
						return this.gameEndSilent();
					}
					else{
						return this.gameEnd();
					}
				}
				
			}
		}
		return (Boolean) null;
		
	}
	
	
	
	
	

	private boolean gameEnd() {

		this.display();
		this.playerHand = new ArrayList<Card>();
		this.dealerHand = new ArrayList<Card>();
		
		if(this.playerValue > 21){
			this.winFlag = false;
			System.out.println("\nBust!");
		}
		else if(this.dealerValue > 21){
			this.winFlag = true;
			System.out.println("\nDealer Bust!");
		}
		else if(this.playerValue == this.dealerValue){
			this.winFlag = false;
			System.out.println("\nPush");
			return true;
		}
		else if(this.playerValue == 21){
			this.winFlag = true;
			System.out.println("\nBlackjack!");
		}
		else if(this.dealerValue == 21){
			this.winFlag = false;
			System.out.println("\nDealer Blackjack!");
		}
		else if(this.playerValue > this.dealerValue){
			this.winFlag = true;
		}
		else if(this.playerValue < this.dealerValue){
			this.winFlag = false;
		}
		
		if(this.winFlag){
			System.out.println("\nYOU WIN!");
		}
		else
			System.out.println("\nYOU LOSE!");
		return true;
	}

	private boolean gameEndSilent() {
		for(int i=0;i<this.playerHand.size();i++){
			this.discardedCards.add(this.playerHand.get(i));
		}
		for(int i=0;i<this.dealerHand.size();i++){
			this.discardedCards.add(this.dealerHand.get(i));
		}
		
		if(this.playerValue > 21){
			this.winFlag = false;
		}
		else if(this.dealerValue > 21){
			this.winFlag=true;
		}
		else if(this.playerValue == this.dealerValue){
			this.winFlag = false;
			return true;
		}
		else if(this.playerValue == 21){
			this.winFlag = true;
		}
		else if(this.dealerValue == 21){
			this.winFlag = false;
		}
		else if(this.playerValue > this.dealerValue){
			this.winFlag = true;
		}
		else if(this.playerValue < this.dealerValue){
			this.winFlag = false;
		}
		return true;
	}

	public void handValue() {
		int totalPlayerValue=0;
		int totalDealerValue=0;
		this.playerAceFlag=false;
		this.dealerAceFlag=false;
		
		for(Card card:this.playerHand){
			if(card.rank==11 || card.rank==12 || card.rank==13 ){
				totalPlayerValue += 10;
			}
			else if(card.rank==1){
				this.playerAceFlag = true;
				totalPlayerValue +=1;
			}
			else{
				totalPlayerValue += card.rank;
			}
		}
		
		for(Card card:this.dealerHand){
			if(card.rank==11 || card.rank==12 || card.rank==13 ){
				totalDealerValue += 10;
			}
			else if(card.rank==1){
				this.dealerAceFlag = true;
				totalDealerValue +=1;
			}
			else{
				totalDealerValue += card.rank;
			}
		}
		
		this.playerValue=totalPlayerValue;
		this.dealerValue=totalDealerValue;
		
		
	}

	public void deal() {
		
		for(int i=0;i<2;i++){
			this.playerHand.add(this.drawFromShoe());
		}
		this.dealerHand.add(this.drawFromShoe());
	}

	public Card drawFromShoe() {
		
		Card newCard=this.shoe.draw();
		if(newCard==null){
			this.reshuffle();
			newCard=this.shoe.draw();
		}
		return newCard;
	}

	public void reshuffle() {
		
		this.shoe=new Shoe(this.numDecks);
		this.shoe.shuffleShoe();
		this.discardedCards=new ArrayList<Card>();
		
	}
	
	public int getPlayerValue(){
		return this.playerValue;
	}
	
	public int getDealerValue(){
		return this.dealerValue;
	}
	
	public boolean getPlayerAceFlag(){
		return this.playerAceFlag;
	}
	
	public boolean getDealerAceFlag(){
		return this.dealerAceFlag;
	}
	
	public List<Card> getDiscardedCards(){
		for(Card card:this.playerHand)
			this.discardedCards.add(card);
		for(Card card:this.dealerHand)
			this.discardedCards.add(card);
		return this.discardedCards;
	}
	
	public List<Card> getPlayerHand(){
		return this.playerHand;
	}
	
	public List<Card> getDealerHand(){
		return this.dealerHand;
	}
	
	public void display(){
		if(!this.playerAceFlag && !this.dealerAceFlag){
			System.out.println("player value=:"+this.playerValue+"\ndealer value=:"+this.dealerValue);
		}
		if(this.playerAceFlag && this.dealerAceFlag){
			System.out.println("player value=:"+this.playerValue+"or"+(this.playerValue+10)+"\ndealer value=:"+this.dealerValue+"or"+(this.dealerValue+10));
		}
		if(this.playerAceFlag && !this.dealerAceFlag){
			System.out.println("player value=:"+this.playerValue+"or"+(this.playerValue+10)+"\ndealer value=:"+this.dealerValue);
		}
		if(!this.playerAceFlag && this.dealerAceFlag){
			System.out.println("player value=:"+this.playerValue+"\ndealer value=:"+this.dealerValue+"or"+(this.dealerValue+10));
		}
	}
	
	
	

}
