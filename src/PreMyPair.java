import javax.swing.JLabel;

public interface PreMyPair {
	public int FRAME_WIDTH = 580; //Declare attribute for setting frame's width. 
	public int FRAME_HEIGHT = 550; //Declare attribute for setting frame's height. 
	public int FRAME_X_ORIGIN = 170; //Declare attribute for setting start point of x-axis of frame.
	public int FRAME_Y_ORIGIN = 50; //Declare attribute for setting start point of y-axis of frame.
	public int ALL_CARDS = 16;
	public int MAX_ROW = 4;
	public int MAX_COLUMN = 4;
	public int FIRST_NAME_PIC_FILE = 1;
	public int AMOUNT_LEVEL = 3;
	public int MAX_PICTURE_BEGINNER = 4;
	public int MAX_PICTURE_INTERMEDIATH = 6;
	public int MAX_PICTURE_EXPERT = 8;
	public int POINT_PER_CARD = 150;
	public int SECOND_CLOSE_CARD = 10;
	public int AMOUNT_DEVELOPER = 7;
	public String ICON_PATH = "pic/icon.jpg";

	public JLabel FREE_LABEL = new JLabel();
	
	public void collectPoint(UserData userData, int cardIndex);
	public void newGame();
	public void loadGame();
	public void saveGame();
	public void changeLevel();
	public void changeDeck();
	public void showHighScore();
	public void showAbout();
	
}
