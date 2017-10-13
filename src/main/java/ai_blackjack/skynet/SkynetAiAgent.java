package ai_blackjack.skynet;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class SkynetAiAgent {
	int count = 0;
	double epsilon;
	double discount;
	double alpha;
	String name;
	int numTraining;
	Dealer dealer;
	HashMap<Pair,Double> qvalues;
	List<Object> info;
	static DecimalFormat df = new DecimalFormat("####0.00");
	String[] HEADERS = { "STATE", "QVALUE", "AI SUGGESTED ACTION", "PLAYER HAND SIZE", "PLAYER HAND VALUE" , "DEALER HAND SIZE" , "DEALER HAND VALUE", "GAME RESULT", "SKYNET HANDLE"};
	
	public SkynetAiAgent(int numDecks,double e,double d, double a,int numTraining, String name){
		this.name=name;
		this.epsilon=e;
		this.discount=d;
		this.alpha=a;
		this.numTraining=numTraining;
		this.dealer=new Dealer(numDecks,true);
		this.qvalues=new HashMap<Pair,Double>();
		this.info = new ArrayList<>();
	}
	
	public int[] getState(){
		int[] state=new int[4];
		state[0]=this.dealer.getPlayerValue();
		state[1]=(this.dealer.getPlayerAceFlag()) ? 1 : 0; 
		state[2]=this.dealer.getDealerValue();
		state[3]=(this.dealer.getDealerAceFlag()) ? 1 : 0;
		return state;
	}
	
	public int getAction(int[] state){
		int[] legalActions=this.getLegalActions(state);
		      
		// get next next boolean value 	      
		if(Math.random() > this.epsilon){
			Random randomG = new Random();
			int index = randomG.nextInt(2);
	        int action = legalActions[index];
			return action; 
		}
		return this.getPolicy(state);
	}

	public void printQvalues(){
		
		qvalues.forEach((pair,reward)->System.out.println("State : " + Arrays.toString(pair.state) + " qvalue : " + df.format(reward) + " AI action(1Hit/2Stay): " + pair.action));
		System.out.println("Hashmap size: " + qvalues.size());		
	}
	
	public void saveQvaluesToCSV() throws IOException{
		
		 FileWriter out = new FileWriter("Qvalues_"+ getName() + ".csv");
		    try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
		      .withHeader(HEADERS))) {
		    	qvalues.forEach((pair, reward) -> {
		    		
		            try {
						 if (count < info.size()) { // Print end of game info
							 printer.printRecord(Arrays.toString(pair.state), reward, pair.action, info.get(count));
						 }
						 else { // Print only state updates
							 printer.printRecord(Arrays.toString(pair.state), reward, pair.action);
						 }
						 count++;
					} catch (Exception e) {
						
					}
		           
		        });
		    	System.out.println("Hashmap size: " + qvalues.size());	
		    }
		
   
	}
	
	public int getPolicy(int[] state) {
		
		double maxValue = -99999.0;
		int maxAction=0;
		double actionValue;
		int[] legalActions=this.getLegalActions(state);
		for(int i=0;i<2;i++){
			actionValue=this.getQValue(state,legalActions[i]);
			if(maxValue < actionValue){
				maxValue = actionValue;
				maxAction = legalActions[i];
			}
		}
		return maxAction;
	}
	
	@SuppressWarnings("unused")
	public double getValue(int[] state) {
		
		double maxValue = -99999.0;
		int maxAction=0;
		double actionValue;
		int[] legalActions=this.getLegalActions(state);
		for(int i=0;i<2;i++){
			actionValue=this.getQValue(state,legalActions[i]);
			if(maxValue < actionValue){
				maxValue = actionValue;
				maxAction = legalActions[i];
			}
		}
		return maxValue;
	}
	
	

	public double getQValue(int[] state, int action) {
		
		Pair pair=new Pair(state,action);
		if(this.qvalues.get(pair) == null)
			return 0.0;
		return this.qvalues.get(pair);
	}

	public int[] getLegalActions(int[] state) {
		
		int[] a=new int[2];
		a[0]=1;
		a[1]=2;
		return a;
	}
	
	public void update(int[] state,int action,int[] nextState,int reward){
		double qvalue=this.getQValue(state, action)+this.alpha*( reward + this.discount * this.getValue(nextState) - this.getQValue(state, action) );
		//System.out.println("Qvalue: " + this.getQValue(state, action)+this.alpha*( reward + this.discount * this.getValue(nextState) - this.getQValue(state, action)));
		//System.out.println("State: " + Arrays.toString(state));
		//System.out.println("Action: "+ action);
		Pair pair=new Pair(state,action);
		this.qvalues.put(pair, qvalue);
	}
	
	public void setEpsilon(double e){
		this.epsilon=e;
	}
	
	public void setDiscount(double d){
		this.discount=d;
	}
	
	public void setAlpha(double a){
		this.alpha=a;
	}
	
	public void setName(String n){
		this.name=n;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Double getEpsilon(){
		return this.epsilon;
	}
	
	public Double getAlpha(){
		return this.alpha;
	}
	
	public Double getDiscount(){
		return this.discount;
	}

}