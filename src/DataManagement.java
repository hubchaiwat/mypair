import java.io.*;

import javax.swing.JOptionPane;

public class DataManagement implements DataInterface{
	
	private File fileSave, fileSetting;
	private FileInputStream inFileStream;
	private DataInputStream inputDataStream;
	private FileOutputStream outFileStream;
	private DataOutputStream outDataStream;
	
	public DataManagement(){
		fileSave = new File(FILE_SAVE_PATH);
		fileSetting = new File(FILE_SETTING_PATH);
		if(fileSave.exists() == false){
			try{
				outFileStream = new FileOutputStream(fileSave);
				outDataStream = new DataOutputStream(outFileStream);
				
				outDataStream.writeBoolean(false);
				
				outDataStream.close();				
			}
			catch(IOException e){
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
		if(fileSetting.exists() == false){
			try{
				outFileStream = new FileOutputStream(fileSetting);
				outDataStream = new DataOutputStream(outFileStream);
				
				outDataStream.writeInt(1);
				outDataStream.writeInt(1);
				
				outDataStream.close();				
			}
			catch(IOException e){
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public DataManagement(String savePath, String settingPath){
		fileSave = new File(savePath);
		fileSetting = new File(settingPath);
		if(fileSave.exists() == false){
			try{
				outFileStream = new FileOutputStream(fileSave);
				outDataStream = new DataOutputStream(outFileStream);
				
				outDataStream.writeBoolean(false);
				
				outDataStream.close();				
			}
			catch(IOException e){
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
		if(fileSetting.exists() == false){
			try{
				outFileStream = new FileOutputStream(fileSetting);
				outDataStream = new DataOutputStream(outFileStream);
				
				outDataStream.writeInt(1);
				outDataStream.writeInt(1);
				
				outDataStream.close();				
			}
			catch(IOException e){
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	@Override
	public boolean checkHaveData(){
		try{
			inFileStream = new FileInputStream(fileSave);
			inputDataStream = new DataInputStream(inFileStream);
			
			boolean haveData = inputDataStream.readBoolean();
			
			inputDataStream.close();
			return haveData;
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
	}
	
	@Override
	public boolean loadGameSetting(UserData userData){		
		try{
			inFileStream = new FileInputStream(fileSetting);
			inputDataStream = new DataInputStream(inFileStream);
			
			userData.setLevel(inputDataStream.readInt());
			
			int numPicDir =  inputDataStream.readInt();
			switch(numPicDir){
				case 1 : userData.setPicDirectory("flower"); break;
				case 2 : userData.setPicDirectory("pet"); break;
				case 3 : userData.setPicDirectory("sushi"); break;
				case 4 : userData.setPicDirectory("cloth"); break;
				case 5 : userData.setPicDirectory("robot"); break;					
			}
						
			inputDataStream.close();
			return true;
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	@Override
	public boolean loadGameData(Card[] card, TimeForward playTime, UserData userData){
		
		try{
			inFileStream = new FileInputStream(fileSave);
			inputDataStream = new DataInputStream(inFileStream);
			
			inputDataStream.readBoolean();
			
			//Setting playTime			
			playTime.setHour(inputDataStream.readInt());
			playTime.setMinute(inputDataStream.readInt());
			playTime.setSecond(inputDataStream.readInt());
			
			//Setting userData			
			userData.setLevel(inputDataStream.readInt());
			userData.setNextLevel(inputDataStream.readInt());
			userData.setScore(inputDataStream.readInt());
			userData.setRemainCard(inputDataStream.readInt());
			userData.setMultiplyScore(inputDataStream.readInt());
			userData.setPreviousSugguest(inputDataStream.readBoolean());
			
			int numPicDir = inputDataStream.readInt();
			
			switch(numPicDir){
				case 1 : userData.setPicDirectory("flower"); break;
				case 2 : userData.setPicDirectory("pet"); break;
				case 3 : userData.setPicDirectory("sushi"); break;
				case 4 : userData.setPicDirectory("cloth"); break;
				case 5 : userData.setPicDirectory("robot"); break;				
			}
			
			for(int i = 0; i < card.length; i++){
				userData.setFlagSugguest(i, inputDataStream.readBoolean());
				card[i] = new Card();				
				card[i].setID(inputDataStream.readInt());
				card[i].setIconPath("pic/"+userData.getPicDirectory()+"/"+card[i].getID()+".jpg");
				card[i].setCreateCardComplete(true);
			}
			
			inputDataStream.close();				
			return true;
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	@Override
	public boolean saveGameData(TimeForward playTime, UserData userData, Card[] card){
		
		try{
			outFileStream = new FileOutputStream(fileSave);
			outDataStream = new DataOutputStream(outFileStream);
			
			//Reset Save Game
			/*outDataStream.writeBoolean(false);
			outDataStream.close();*/
			
			//Setting for have data
			outDataStream.writeBoolean(true);
			
			//Begin save playTime
			outDataStream.writeInt(playTime.getHour());
			outDataStream.writeInt(playTime.getMinute());
			outDataStream.writeInt(playTime.getSecond());
			
			//Begin save userData
			outDataStream.writeInt(userData.getLevel());
			outDataStream.writeInt(userData.getNextLevel());
			outDataStream.writeInt(userData.getScore());
			outDataStream.writeInt(userData.getRemainCard());
			outDataStream.writeInt(userData.getMultiplyScore());
			outDataStream.writeBoolean(userData.getPreviousSugguest());
			
			//Compare for write picDirectory
			if(userData.getPicDirectory().equals("flower"))
				outDataStream.writeInt(1);
			else if(userData.getPicDirectory().equals("pet"))
				outDataStream.writeInt(2);
			else if(userData.getPicDirectory().equals("sushi"))
				outDataStream.writeInt(3);
			else if(userData.getPicDirectory().equals("cloth"))
				outDataStream.writeInt(4);
			else if(userData.getPicDirectory().equals("robot"))
				outDataStream.writeInt(5);
			
			for(int i = 0; i < card.length; i++){
				outDataStream.writeBoolean(userData.getFlagSugguest(i));
				outDataStream.writeInt(card[i].getID());
			}			
			
			outDataStream.close();
			return true;
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			return false;
		}	
		
	}
	
	@Override
	public boolean changeLevelData(UserData userData){
			
		try {
			outFileStream = new FileOutputStream(fileSetting);
			outDataStream = new DataOutputStream(outFileStream);
						
			outDataStream.writeInt(userData.getNextLevel());
			if(userData.getPicDirectory().equals("flower"))
				outDataStream.writeInt(1);
			else if(userData.getPicDirectory().equals("pet"))
				outDataStream.writeInt(2);
			else if(userData.getPicDirectory().equals("sushi"))
				outDataStream.writeInt(3);
			else if(userData.getPicDirectory().equals("cloth"))
				outDataStream.writeInt(4);
			else if(userData.getPicDirectory().equals("robot"))
				outDataStream.writeInt(5);
			
			outDataStream.close();
			return true;			
		} 
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			return false;
		}	
	}
	
	@Override
	public boolean changeDeckData(UserData userData){
		
		try {
			outFileStream = new FileOutputStream(fileSetting);
			outDataStream = new DataOutputStream(outFileStream);
						
			outDataStream.writeInt(userData.getNextLevel());
			if(userData.getPicDirectory().equals("flower"))
				outDataStream.writeInt(1);
			else if(userData.getPicDirectory().equals("pet"))
				outDataStream.writeInt(2);
			else if(userData.getPicDirectory().equals("sushi"))
				outDataStream.writeInt(3);
			else if(userData.getPicDirectory().equals("cloth"))
				outDataStream.writeInt(4);
			else if(userData.getPicDirectory().equals("robot"))
				outDataStream.writeInt(5);
			
			outDataStream.close();			
			return true;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
}
