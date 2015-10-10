
import javax.swing.*;
import java.util.Random;

public class Card extends JButton{
	public static final int DEFAULT_ID = 0;
	private static final Icon iconClose =  new ImageIcon("pic/back.jpg");
	private Icon iconOpen;
	private int cardID;
	private boolean createCardComplete;
	
	public Card(){
		this.setSize(100, 100);
		this.setCreateCardComplete(false);
		setID(DEFAULT_ID);
		iconOpen = new ImageIcon();
		this.closeCard();
	}
	
	public Card(int ID){
		this.setSize(100, 100);
		this.setCreateCardComplete(false);
		setID(ID);
		iconOpen = new ImageIcon();
		this.closeCard();	
	}	
	
	public static void refreshEachCard(Card card, UserData userData, int indexCard){
		if(userData.getFlagSugguest(indexCard) == false)
			card.closeCard();
		else
			card.openCard();
	}
	
	public static void refreshSetCard(Card[] card, UserData userData){
		for(int i = 0; i < card.length; i++){
			if(userData.getFlagSugguest(i) == false)
				card[i].closeCard();
			else
				card[i].openCard();			
		}
	}	
	
	public static int getRandomNumber(int maxNumber, int minNumber){
		Random random = new Random();
		return random.nextInt(maxNumber-minNumber+1)+minNumber;
	}
	
	public static void createEachCard(Card[] card, String picDirectory, int cardID){		
		while(true){
			int numCard = getRandomNumber(MyPair.ALL_CARDS-1,0);
			if(card[numCard].createCardComplete == false){
				String picPath = "";
				card[numCard].cardID = cardID;
				
				picPath = picPath+"pic/"+picDirectory+"/"+cardID+".jpg";
				card[numCard].setIconPath(picPath);
				card[numCard].createCardComplete = true;
				break;
			}
		}
	}
	
	public static void createSetCardBeginner(Card[] card, String picDirectory, int maxPicture){
		
		for(int i = 0; i < card.length; i++){
			card[i] = new Card();
		}
		
		int cardSort = 1;
		while(cardSort <= maxPicture){		
			for(int i = 0; i < 4; i++)
				createEachCard(card, picDirectory, cardSort);		
			cardSort = cardSort+1;
		}
	}
	
	public static void createSetCardImmediate(Card[] card, String picDirectory, int maxPicture){
		for(int i = 0; i < card.length; i++){
			card[i] = new Card();
		}
		
		int cardSort = 1;
		while(cardSort <= maxPicture){
			for(int i = 0; i < 2; i++)
				createEachCard(card, picDirectory, cardSort);
			cardSort = cardSort+1;
		}
		
		int numBonus = getRandomNumber(maxPicture,1);
		for(int i = 0; i < 2; i++)
			createEachCard(card, picDirectory, numBonus);
		
		numBonus = getRandomNumber(maxPicture,1);
		for(int i = 0; i < 2; i++)
			createEachCard(card, picDirectory, numBonus);
	}
	
	public static void createSetCardExpert(Card[] card, String picDirectory, int maxPicture){
		for(int i = 0; i < card.length; i++){
			card[i] = new Card();
		}
		
		int cardSort = 1;
		while(cardSort <= maxPicture){
			for(int i = 0; i < 2; i++)
				createEachCard(card, picDirectory, cardSort);
			
			cardSort = cardSort+1;
		}
	}
	
	public void setID(int ID){
		this.cardID = ID;
	}
	
	public int getID(){
		return this.cardID;
	}
	
	public void setIconPath(String picPath){
		iconOpen = new ImageIcon(picPath);
	}
	
	public void openCard(){
		this.setIcon(iconOpen);
	}
	
	public void closeCard(){
		this.setIcon(iconClose);
	}
	
	public void setCreateCardComplete(boolean createCardComplete){
		this.createCardComplete = createCardComplete;
	}
	
	public boolean getCreateCardComplete(){
		return this.createCardComplete;
	}	
}
