
public class UserData {
	
	public static final int NOT_CHOOSING = -1;
	public static final int BEGIN_MULTIPLY_SCORE = 1;
	public static final String DEFAULT_PIC_DIRECTORY = "UNKONWN";
	
	private int level, nextLevel, score, remainCard, amountChoosing, firstCardIndex, secondCardIndex, previousCardIndex, multiplyScore;	
	private String picDirectory;
	private boolean[] flagSugguest;
	private boolean previousSugguest;
	
	public UserData(){
		this.setPreviousCardIndex(NOT_CHOOSING);
		this.setLevel(1);
		this.setNextLevel(1);
		this.setScore(0);
		this.setRemainCard(MyPair.ALL_CARDS);
		this.setAmountChoosing(0);
		this.setFirstCardIndex(NOT_CHOOSING);
		this.setSecondCardIndex(NOT_CHOOSING);
		this.setPicDirectory(DEFAULT_PIC_DIRECTORY);
		this.flagSugguest = new boolean[MyPair.ALL_CARDS];
		for(int i = 0; i < MyPair.ALL_CARDS; i++){
			this.setFlagSugguest(i, false);
		}
		this.setMultiplyScore(BEGIN_MULTIPLY_SCORE);
		this.setPreviousSugguest(false);
	}
	
	public UserData(int level, int score, int remainCard){
		this.setPreviousCardIndex(NOT_CHOOSING);
		this.setLevel(level);
		this.setNextLevel(1);
		this.setScore(score);
		this.setRemainCard(remainCard);
		this.setAmountChoosing(0);
		this.setFirstCardIndex(NOT_CHOOSING);
		this.setSecondCardIndex(NOT_CHOOSING);
		this.setPicDirectory(DEFAULT_PIC_DIRECTORY);
		this.flagSugguest = new boolean[MyPair.ALL_CARDS];
		for(int i = 0; i < MyPair.ALL_CARDS; i++){
			this.setFlagSugguest(i, false);
		}
		this.setMultiplyScore(BEGIN_MULTIPLY_SCORE);
		this.setPreviousSugguest(false);
	}
	
	public String getStringLevel(){
		String strLevel = "";
		if(this.level == 1)
			strLevel = "BEGINNER";
		else if(this.level == 2)
			strLevel = "INTERMEDIATE";
		else if(this.level == 3)
			strLevel = "EXPERT";
		return strLevel;
		
	}
	
	public String getStringNextLevel(){
		String strLevel = "";
		if(this.nextLevel == 1)
			strLevel = "BEGINNER";
		else if(this.nextLevel == 2)
			strLevel = "INTERMEDIATE";
		else if(this.nextLevel == 3)
			strLevel = "EXPERT";
		return strLevel;
	}
	
	public void decreaseRemainCrad(){
		this.remainCard = this.remainCard-2;
	}
	
	public void increaseScore(){
		this.score = this.score+(MyPair.POINT_PER_CARD*multiplyScore);
	}
	
	public void plusScore(int score){
		this.score = this.score+score;
	}
	
	public boolean getFinishGame(){
		if(remainCard == 0)
			return true;
		else
			return false;
	}
	
	public void setLevel(int level){
		this.level = level;
	}
	
	public int getLevel(){
		return this.level;
	}
	
	public void setNextLevel(int nextLevel){
		this.nextLevel = nextLevel;
	}
	
	public int getNextLevel(){
		return this.nextLevel;
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public int getScore(){
		return this.score;
	}
	
	public String getStringScore(){
		String tempScore = "Score: "+this.score;
		if(this.multiplyScore > 1)
			tempScore = tempScore+" X "+this.multiplyScore; 
		return tempScore;
	}
	
	public void setRemainCard(int remainCard){
		this.remainCard = remainCard;
	}
	
	public int getRemainCard(){
		return this.remainCard;
	}
	
	public void setAmountChoosing(int amountChoosing){
		this.amountChoosing = amountChoosing;
	}
	
	public int getAmountChoosing(){
		return this.amountChoosing;
	}
	
	public void setFirstCardIndex(int firstCardIndex){
		this.firstCardIndex = firstCardIndex;
	}
	
	public int getFirstCardIndex(){
		return this.firstCardIndex;
	}
	
	public void setSecondCardIndex(int secondCardIndex){
		this.secondCardIndex = secondCardIndex;
	}
	
	public int getSecondCardIndex(){
		return this.secondCardIndex;
	}
	
	public void setPreviousCardIndex(int previousCardIndex) {
		this.previousCardIndex = previousCardIndex;
	}
	
	public int getPreviousCardIndex() {
		return previousCardIndex;
	}	

	public void setPicDirectory(String picDirectory){
		this.picDirectory = picDirectory;
	}
	
	public String getPicDirectory(){
		return this.picDirectory;
	}
	
	public void setFlagSugguest(int index, boolean flagValue){
		this.flagSugguest[index] = flagValue;
	}
	
	public boolean getFlagSugguest(int index){
		return flagSugguest[index];
	}
	
	public void setMultiplyScore(int multiplyScore){
		this.multiplyScore = multiplyScore;
	}
	
	public int getMultiplyScore(){
		return this.multiplyScore;
	}
	
	public void setPreviousSugguest(boolean previousSugguest){
		this.previousSugguest = previousSugguest;
	}
	
	public boolean getPreviousSugguest(){
		return this.previousSugguest;
	}
	
	
}
