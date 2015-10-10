
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MyPair extends JFrame implements Runnable, ActionListener, WindowListener, PreMyPair{
	
	//General Attribute
	private Thread clockThread;	
	
	//Attribute which create with my classes
	private UserData userData;
	private Card[] card;
	private TimeForward playTime;
	private TimeBackward closeCard;
	private DataManagement dataManage;
	private HighScore highScore;
	private SaveLog saveLog;
	
	//Attribute for frame
	private GridBagConstraints constraint;
	private JLabel levelLabel, scoreLabel, timeLabel, remainCardLabel;
	private JPanel panelHead, panelFoot, panelCard;
	private JMenuBar menuBar;
	private JMenu gameMenu, helpMenu; 
	private JMenuItem aboutMenu, newMenu, changeLevelMenu, changeDeckMenu, highScoreMenu, exitMenu, loadMenu, saveMenu;
	
	
	//Method for set position of tool objects on the frame
	public void addFrameComponent(JFrame f, Component c, int x, int y, int width, int height, int fill, int a){
		constraint.gridx = x;
		constraint.gridy = y;
		constraint.gridwidth = width;
		constraint.gridheight = height;
		constraint.fill = fill;
		constraint.anchor = a;
		f.add(c, constraint);
	}
	
	public MyPair(){

		//Create Object 
		userData = new UserData();
		userData.setRemainCard(ALL_CARDS);
		
		dataManage = new DataManagement();
		highScore = new HighScore();
		saveLog = new SaveLog();
		
		playTime = new TimeForward();
		closeCard = new TimeBackward();
		closeCard.setSecond(SECOND_CLOSE_CARD);
		
		dataManage.loadGameSetting(userData);
				
		//Setting component of frame 
		//this.setResizable(false);
		constraint = new GridBagConstraints();
		setLayout(new GridBagLayout());		
		setSize(FRAME_WIDTH, FRAME_HEIGHT); //Setting size of frame.
		setTitle("My Pair"); //Setting title of frame.
		setLocation(FRAME_X_ORIGIN,FRAME_Y_ORIGIN); //Setting x-axis and y-axis of frame.
		setIconImage(Toolkit.getDefaultToolkit().getImage(ICON_PATH));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addWindowListener(this);
		
		//Initialize Menu Bar
		//Create MenuBar
		menuBar = new JMenuBar();
		
		//Menu Game
		gameMenu = new JMenu("Game");		
		newMenu = new JMenuItem("New Game");
		loadMenu = new JMenuItem("Load Game");
		saveMenu = new JMenuItem("Save Game");
		changeLevelMenu = new JMenuItem("Change Level");
		changeDeckMenu = new JMenuItem("Change Deck");
		highScoreMenu = new JMenuItem("High Score");
		exitMenu = new JMenuItem("Exit");
		
		//Add ActionListener to menus
		newMenu.addActionListener(this);
		loadMenu.addActionListener(this);
		saveMenu.addActionListener(this);
		changeLevelMenu.addActionListener(this);
		changeDeckMenu.addActionListener(this);
		highScoreMenu.addActionListener(this);
		exitMenu.addActionListener(this);	
		
		//Add menus to gameMenu
		gameMenu.add(newMenu);
		gameMenu.add(loadMenu);
		gameMenu.add(saveMenu);
		gameMenu.add(changeLevelMenu);
		gameMenu.add(changeDeckMenu);
		gameMenu.add(highScoreMenu);
		gameMenu.add(exitMenu);
		
		//Menu Help
		helpMenu = new JMenu("Help");
		aboutMenu = new JMenuItem("About MyPair");
		aboutMenu.addActionListener(this);
		helpMenu.add(aboutMenu);
		
		menuBar.add(gameMenu);
		menuBar.add(helpMenu);
		
		//Set MenuBar in frame
		this.setJMenuBar(menuBar);		
		
		//Initialize panelHead
		//Create Panel
		panelHead = new JPanel();
		panelHead.setLayout(new GridLayout(1,4));
		levelLabel = new JLabel("Level: "+userData.getStringLevel());
		scoreLabel = new JLabel();		
		
		//Add labels to panelHead
		panelHead.add(levelLabel);
		panelHead.add(FREE_LABEL);
		panelHead.add(FREE_LABEL);
		panelHead.add(scoreLabel);
		
		//Initialize panelCard		
		panelCard = new JPanel();
		card = new Card[16];		
		panelCard.setLayout(new GridLayout(MAX_ROW, MAX_COLUMN));
		panelCard.setSize(400, 400);
		if(userData.getLevel() == 1)
			Card.createSetCardBeginner(card, userData.getPicDirectory(),MAX_PICTURE_BEGINNER);
		else if(userData.getLevel() == 2)
			Card.createSetCardImmediate(card, userData.getPicDirectory(),MAX_PICTURE_INTERMEDIATH);
		else if(userData.getLevel() == 3)
			Card.createSetCardExpert(card, userData.getPicDirectory(), MAX_PICTURE_EXPERT);
				
		int cardNum = 0;
		for(int i = 0; i < MAX_COLUMN; i++){
			for(int j = 0; j < MAX_ROW; j++){
				panelCard.add(card[cardNum]);
				card[cardNum].addActionListener(this);
				cardNum = cardNum+1;
			}
		}
		
		//Initialize panelFoot
		panelFoot = new JPanel();
		panelFoot.setLayout(new GridLayout(1,4));	
		timeLabel = new JLabel();
		remainCardLabel = new JLabel();
		panelFoot.add(remainCardLabel);
		panelFoot.add(FREE_LABEL);
		panelFoot.add(FREE_LABEL);
		panelFoot.add(timeLabel);
		
		//Add all panels to frame
		addFrameComponent(this, panelHead, 0,1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER);
		addFrameComponent(this, panelCard, 0,2,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER);
		addFrameComponent(this, panelFoot, 0,3,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER);
		
		this.start();
		this.setVisible(true);
		saveLog.recordOpen();
		saveLog.recordBeginPlayGame();
	}
	
	public static void main(String[] args) {
		new MyPair();
	}
	
	public void start(){
		if(clockThread == null){
			clockThread = new Thread(this);
			clockThread.start();
		}			
	}
	
	public void stop(){
		if(clockThread != null){
			clockThread.stop();
			clockThread = null;
		}
		
	}
	
	@Override
	public void run(){
		while(true){
			
			remainCardLabel.setText("Remain Card : "+ userData.getRemainCard());
			timeLabel.setText("Play Time "+playTime.getStringTime());
			scoreLabel.setText("                            "+userData.getStringScore());
			if(userData.getFinishGame() == true){
				playTime.stop();
				saveLog.recordFinishGame();
				this.showHighScore();
				this.stop();
			}
			if(closeCard.getSecond() == TimeBackward.MIN_SECOND){
				for(int i = 0; i < ALL_CARDS; i++)
					Card.refreshEachCard(card[i], userData, i);
				userData.setAmountChoosing(0);
				userData.setPreviousCardIndex(UserData.NOT_CHOOSING);
				userData.setFirstCardIndex(UserData.NOT_CHOOSING);
				userData.setSecondCardIndex(UserData.NOT_CHOOSING);
				closeCard.stop();
				closeCard.setSecond(10);
			}
				
			try{
				Thread.sleep(10);
			}
			catch(Exception ex){
				
			}
		}
	}
	
	@Override
	public void collectPoint(UserData userData, int cardIndex){	
		if(userData.getPreviousCardIndex() != cardIndex){
			if((userData.getAmountChoosing() == 0)||(userData.getAmountChoosing() == 3)){
				userData.setFirstCardIndex(cardIndex);
				card[userData.getFirstCardIndex()].openCard();
				userData.setAmountChoosing(1);
				userData.setSecondCardIndex(UserData.NOT_CHOOSING);
				userData.setPreviousCardIndex(cardIndex);
				for(int i = 0; i < ALL_CARDS; i++){
					if(i != cardIndex){
						Card.refreshEachCard(card[i], userData, i);
					}
				}
				closeCard.stop();
				closeCard.setSecond(SECOND_CLOSE_CARD);
			}
			else if(userData.getAmountChoosing() == 1){
				userData.setSecondCardIndex(cardIndex);
				card[userData.getSecondCardIndex()].openCard();
				int strFirstCardID = card[userData.getFirstCardIndex()].getID();
				int strSecondCardID = card[userData.getSecondCardIndex()].getID();
				if(strFirstCardID == strSecondCardID){
					if(userData.getPreviousSugguest() == true)
						userData.setMultiplyScore(userData.getMultiplyScore()+1);
					userData.increaseScore();
					userData.decreaseRemainCrad();
					card[userData.getFirstCardIndex()].removeActionListener(this);
					card[userData.getSecondCardIndex()].removeActionListener(this);
					userData.setFlagSugguest(userData.getFirstCardIndex(), true);
					userData.setFlagSugguest(userData.getSecondCardIndex(), true);
					
					userData.setPreviousSugguest(true);
				}
				else{
					userData.setPreviousSugguest(false);
					userData.setMultiplyScore(UserData.BEGIN_MULTIPLY_SCORE);
					closeCard.setSecond(SECOND_CLOSE_CARD);
					closeCard.start();
				}
												
				userData.setFirstCardIndex(UserData.NOT_CHOOSING);
				userData.setSecondCardIndex(UserData.NOT_CHOOSING);
				userData.setAmountChoosing(0);
				userData.setPreviousCardIndex(cardIndex);				
			}		
		}	
	}
	
	@Override
	public void newGame(){
		this.stop();
		
		//Remove all panels from frame
		this.remove(panelHead);
		this.remove(panelCard);
		this.remove(panelFoot);
		
		//Create Object
		
		int tempLevel = userData.getNextLevel();
		String tempPicDirectory = userData.getPicDirectory();
		userData = new UserData();
		userData.setLevel(tempLevel);
		userData.setNextLevel(tempLevel);
		userData.setPicDirectory(tempPicDirectory);
		userData.setRemainCard(ALL_CARDS);
		
		playTime = new TimeForward();
		
		closeCard = new TimeBackward();
		closeCard.setSecond(SECOND_CLOSE_CARD);
		
		//Initialize panelHead
		//Create Panel
		panelHead = new JPanel();
		panelHead.setLayout(new GridLayout(1,4));
		levelLabel = new JLabel("Level: "+userData.getStringLevel());
		scoreLabel = new JLabel();		
		
		//Add labels to panelHead
		panelHead.add(levelLabel);
		panelHead.add(FREE_LABEL);
		panelHead.add(FREE_LABEL);
		panelHead.add(scoreLabel);
		
		//Initialize panelCard		
		panelCard = new JPanel();
		card = new Card[ALL_CARDS];		
		panelCard.setLayout(new GridLayout(MAX_ROW, MAX_COLUMN));
		panelCard.setSize(400, 400);
		if(userData.getNextLevel() == 1)
			Card.createSetCardBeginner(card, userData.getPicDirectory(),MAX_PICTURE_BEGINNER);
		else if(userData.getNextLevel() == 2)
			Card.createSetCardImmediate(card, userData.getPicDirectory(),MAX_PICTURE_INTERMEDIATH);
		else if(userData.getNextLevel() == 3)
			Card.createSetCardExpert(card, userData.getPicDirectory(),MAX_PICTURE_EXPERT);
				
		int cardNum = 0;
		for(int i = 0; i < MAX_COLUMN; i++){
			for(int j = 0; j < MAX_ROW; j++){
				panelCard.add(card[cardNum]);
				card[cardNum].addActionListener(this);
				cardNum = cardNum+1;
			}
		}
		
		//Initialize panelFoot
		panelFoot = new JPanel();
		panelFoot.setLayout(new GridLayout(1,4));	
		timeLabel = new JLabel();
		remainCardLabel = new JLabel();
		panelFoot.add(remainCardLabel);
		panelFoot.add(FREE_LABEL);
		panelFoot.add(FREE_LABEL);
		panelFoot.add(timeLabel);
		
		//Add all panels to frame
		addFrameComponent(this, panelHead, 0,1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER);
		addFrameComponent(this, panelCard, 0,2,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER);
		addFrameComponent(this, panelFoot, 0,3,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER);
		
		this.start();
		this.setVisible(true);
		saveLog.recordNewGame();
		saveLog.recordBeginPlayGame();
	}
	
	@Override
	public void loadGame(){
		Object[] options = {"Yes","No"};
		int numOption = JOptionPane.showOptionDialog(null,"Do you want to load game?","Load Game",
			JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
		
		if(numOption == 0){
			if(dataManage.checkHaveData() == true){
				
				this.stop();
				
				//Remove all panels from frame
				this.remove(panelHead);
				this.remove(panelCard);
				this.remove(panelFoot);
				
				//Create objects		
				card = new Card[ALL_CARDS];
				playTime = new TimeForward();
				userData = new UserData();
				
				boolean complete = dataManage.loadGameData(card, playTime, userData);
				
				closeCard = new TimeBackward();
				closeCard.setSecond(SECOND_CLOSE_CARD);	
				
				//Initialize panelHead
				//Create Panel
				panelHead = new JPanel();
				panelHead.setLayout(new GridLayout(1,4));
				levelLabel = new JLabel("Level: "+userData.getStringLevel());
				scoreLabel = new JLabel();		
				
				//Add labels to panelHead
				panelHead.add(levelLabel);
				panelHead.add(FREE_LABEL);
				panelHead.add(FREE_LABEL);
				panelHead.add(scoreLabel);
				
				//Initialize panelCard		
				panelCard = new JPanel();		
				panelCard.setLayout(new GridLayout(MAX_ROW, MAX_COLUMN));
				panelCard.setSize(400, 400);
						
				int cardNum = 0;
				for(int i = 0; i < MAX_COLUMN; i++){
					for(int j = 0; j < MAX_ROW; j++){
						panelCard.add(card[cardNum]);
						card[cardNum].addActionListener(this);
						cardNum = cardNum+1;
					}
				}
				
				//Initialize panelFoot
				panelFoot = new JPanel();
				panelFoot.setLayout(new GridLayout(1,4));	
				timeLabel = new JLabel();
				remainCardLabel = new JLabel();
				panelFoot.add(remainCardLabel);
				panelFoot.add(FREE_LABEL);
				panelFoot.add(FREE_LABEL);
				panelFoot.add(timeLabel);
				
				//Add all panels to frame
				addFrameComponent(this, panelHead, 0,1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER);
				addFrameComponent(this, panelCard, 0,2,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER);
				addFrameComponent(this, panelFoot, 0,3,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER);
				
				Card.refreshSetCard(card, userData);
				
				this.start();
				this.setVisible(true);				
				
				if(complete == true){
					saveLog.recordLoadGame();
					JOptionPane.showMessageDialog(null, "Load game complete!");
				}			
			}
			else
				JOptionPane.showMessageDialog(null, "You not have save game!", "Load Game", JOptionPane.PLAIN_MESSAGE);	
		}									
	}
	
	@Override
	public void saveGame(){
		int numOption = 0;
		
		if(userData.getRemainCard() > 0){
			Object[] options = {"Yes","No"};
			numOption = JOptionPane.showOptionDialog(null,"Do you want to save game?","Save Game",
				JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
		}
		else
			numOption = 1;
			
		if(numOption == 0){
			boolean complete = dataManage.saveGameData(playTime, userData, card);
			if(complete == true){
				saveLog.recordSaveGame();
				JOptionPane.showMessageDialog(null, "Save game complete!");
			}			
		}
	}
	
	@Override
	public void changeLevel(){		
		
		Object[] levelObj = {"Beginner", "Intermediate", "Expert"};
		String levelStr = (String)JOptionPane.showInputDialog(null,"Please select level!",
				"Change Level",JOptionPane.PLAIN_MESSAGE,null,levelObj,"Beginner");
		
		if(levelStr != null){
			String currentLevel , nextLevel;
			currentLevel = userData.getStringLevel();
			
			if(levelStr.equals("Beginner"))
				userData.setNextLevel(1);
			else if(levelStr.equals("Intermediate"))
				userData.setNextLevel(2);
			else if(levelStr.equals("Expert"))
				userData.setNextLevel(3);
			;
			
			nextLevel = userData.getStringNextLevel();
			
			boolean complete = dataManage.changeLevelData(userData);
			if(complete == true){
				saveLog.recordChangeLevel(currentLevel, nextLevel);
				JOptionPane.showMessageDialog(null, "Level will change in next game!");	
			}				
		}				
	}
	
	@Override
	public void changeDeck(){
		Object[] deckObj = {"Flower", "Pet", "Sushi", "Cloth", "Robot"};
		String deckStr = (String)JOptionPane.showInputDialog(null,"Please select deck!",
		           "Change Level",JOptionPane.PLAIN_MESSAGE,null,deckObj,"Beginner");
		
		if(deckStr != null){
			String currentDeck, nextDeck;
			currentDeck = userData.getPicDirectory();
			
			if(deckStr.equals("Flower"))
				userData.setPicDirectory("flower");
			else if(deckStr.equals("Pet"))
				userData.setPicDirectory("pet");
			else if(deckStr.equals("Sushi"))
				userData.setPicDirectory("sushi");
			else if(deckStr.equals("Cloth"))
				userData.setPicDirectory("cloth");
			else if(deckStr.equals("Robot"))
				userData.setPicDirectory("robot");
			
			nextDeck = userData.getPicDirectory();
			
			boolean complete = dataManage.changeDeckData(userData);
			if(complete == true){
				saveLog.recordChangeDeck(currentDeck, nextDeck);
				JOptionPane.showMessageDialog(null, "Deck will change in next game!");	
			}
		}
	}
	
	@Override
	public void showHighScore(){		
		highScore.showHighScore(userData);		
	}
	
	@Override
	public void showAbout(){
		new AboutMyPair();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == card[0])
			collectPoint(userData, 0);		
		else if(e.getSource() == card[1])
			collectPoint(userData, 1);
		
		else if(e.getSource() == card[2])
			collectPoint(userData, 2);
		
		else if(e.getSource() == card[3])
			collectPoint(userData, 3);
		
		else if(e.getSource() == card[4])
			collectPoint(userData, 4);
		
		else if(e.getSource() == card[5])
			collectPoint(userData, 5);
		
		else if(e.getSource() == card[6])
			collectPoint(userData, 6);
		
		else if(e.getSource() == card[7])
			collectPoint(userData, 7);
		
		else if(e.getSource() == card[8])
			collectPoint(userData, 8);
		
		else if(e.getSource() == card[9])
			collectPoint(userData, 9);
		
		else if(e.getSource() == card[10])
			collectPoint(userData, 10);
		
		else if(e.getSource() == card[11])
			collectPoint(userData, 11);
		
		else if(e.getSource() == card[12])
			collectPoint(userData, 12);
		
		else if(e.getSource() == card[13])
			collectPoint(userData, 13);
		
		else if(e.getSource() == card[14])
			collectPoint(userData, 14);
		
		else if(e.getSource() == card[15])
			collectPoint(userData, 15);
		
		else if(e.getSource() == newMenu){
			
			Object[] options = {"Yes","No"};
			int numOption = JOptionPane.showOptionDialog(null,"Do you want to new game?","New Game",
					JOptionPane.YES_NO_OPTION,
				    JOptionPane.QUESTION_MESSAGE,
				    null,
				    options,
				    options[1]);
			
			if(numOption == 0){
				this.newGame();	
			}			
		}
		else if(e.getSource() == loadMenu)
			this.loadGame();
		
		else if(e.getSource() == saveMenu)	
			this.saveGame();
		
		else if(e.getSource() == changeLevelMenu)
			this.changeLevel();
		
		else if(e.getSource() == changeDeckMenu)
			this.changeDeck();
		
		else if(e.getSource() == highScoreMenu)
			this.showHighScore();
		
		else if(e.getSource() == exitMenu){
			Object[] options = {"Yes","No"};
			int numExit = JOptionPane.showOptionDialog(null,"Do you want to exit game?","Exit Game!",
				JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
				
			if(numExit== 0){
				int saveOption = 0;
				
				if(userData.getFinishGame() == false){
					saveOption = JOptionPane.showOptionDialog(null,"Do you want to save game?","Save Game",
						JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
				}
				else
					saveOption = 1;
					
				if(saveOption == 0){
					boolean complete = dataManage.saveGameData(playTime, userData, card);
					if(complete == true){
						saveLog.recordSaveGame();
						JOptionPane.showMessageDialog(null, "Save game complete!\n Please click OK for exit MyPair!");
					}			
				}
				saveLog.recordClose();
				System.exit(0);
			}
									
			
		}
		else if(e.getSource() == aboutMenu)
			this.showAbout();
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		int saveOption = 0;
		Object[] options = {"Yes","No"};
		if(userData.getFinishGame() == false){
			saveOption = JOptionPane.showOptionDialog(null,"Do you want to save game?","Save Game",
				JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
		}
		else
			saveOption = 1;
			
		if(saveOption == 0){
			boolean complete = dataManage.saveGameData(playTime, userData, card);
			if(complete == true){
				saveLog.recordSaveGame();
				JOptionPane.showMessageDialog(null, "Save game complete!\n Please click OK for exit MyPair!");
			}			
		}
		saveLog.recordClose();
		System.exit(0);
	}		
	
	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {		
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
			
	}
}	