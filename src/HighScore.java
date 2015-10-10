import java.io.*;
import javax.swing.JOptionPane;

public class HighScore {
	
	public static String DEFAULT_PATH = "data/highscore.mps";
	public static int DEFAULT_MAX_LEVEL = 3;
	
	private int[] highScore, lastPlay;
	private File file;
	private FileInputStream inFileStream;
	private DataInputStream inDataStream;
	private FileOutputStream outFileStream;
	private DataOutputStream outputStream;
	
	public HighScore(){
		highScore = new int[DEFAULT_MAX_LEVEL];
		lastPlay = new int[DEFAULT_MAX_LEVEL];
		file = new File(DEFAULT_PATH);
		if(file.exists() == true){
			try{
				inFileStream = new FileInputStream(file);
				inDataStream = new DataInputStream(inFileStream);
				
				for(int i = 0; i < highScore.length; i++){
					highScore[i] = inDataStream.readInt();
					lastPlay[i] = inDataStream.readInt();
				}
				inDataStream.close();
			}
			catch(IOException e){
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
		else{
			try {
				outFileStream = new FileOutputStream(file);
				outputStream = new DataOutputStream(outFileStream);
							
				for(int i = 0; i < DEFAULT_MAX_LEVEL; i++){
					highScore[i] = 0;
					lastPlay[i] = 0;
					
					outputStream.writeInt(highScore[i]);
					outputStream.writeInt(lastPlay[i]);
				}
				outputStream.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public HighScore(String highScorePath){
		highScore = new int[DEFAULT_MAX_LEVEL];
		lastPlay = new int[DEFAULT_MAX_LEVEL];
		file = new File(highScorePath);
		if(file.exists() == true){
			try{
				inFileStream = new FileInputStream(file);
				inDataStream = new DataInputStream(inFileStream);
				
				for(int i = 0; i < DEFAULT_MAX_LEVEL; i++){
					highScore[i] = inDataStream.readInt();
					lastPlay[i] = inDataStream.readInt();
				}
				inDataStream.close();
			}
			catch(IOException e){
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
		else{
			try {
				outFileStream = new FileOutputStream(file);
				outputStream = new DataOutputStream(outFileStream);
							
				for(int i = 0; i < highScore.length; i++){
					highScore[i] = 0;
					lastPlay[i] = 0;
					
					outputStream.writeInt(highScore[i]);
					outputStream.writeInt(lastPlay[i]);
				}
				outputStream.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public HighScore(String highScorePath, int maxLevel){
		highScore = new int[maxLevel];
		lastPlay = new int[maxLevel];
		file = new File(highScorePath);
		if(file.exists() == true){
			try{
				inFileStream = new FileInputStream(file);
				inDataStream = new DataInputStream(inFileStream);
				
				for(int i = 0; i < DEFAULT_MAX_LEVEL; i++){
					highScore[i] = inDataStream.readInt();
					lastPlay[i] = inDataStream.readInt();
				}
				inDataStream.close();
			}
			catch(IOException e){
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
		else{
			try {
				outFileStream = new FileOutputStream(file);
				outputStream = new DataOutputStream(outFileStream);
							
				for(int i = 0; i < highScore.length; i++){
					highScore[i] = 0;
					lastPlay[i] = 0;
					
					outputStream.writeInt(highScore[i]);
					outputStream.writeInt(lastPlay[i]);
				}
				outputStream.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void setHighScore(int level, int highScore){
		this.highScore[level-1] = highScore;
	}
	
	public void setHighScore(String stringLevel, int highScore){
		stringLevel = stringLevel.toUpperCase();
		
		if(stringLevel.equals("BEGINNER"))
			this.highScore[0] = highScore;
		else if(stringLevel.equals("IMMEDIATE"))
			this.highScore[1] = highScore;
		else if(stringLevel.equals("EXPERT"))
			this.highScore[1] = highScore;
	}
	
	public int getHighScore(int level){
		return this.highScore[level-1];
	}
	
	public int getHighScore(String stringLevel){
		int highScore = 0;
		
		stringLevel = stringLevel.toUpperCase();
		
		if(stringLevel.equals("BEGINNER"))
			highScore = this.highScore[0];
		else if(stringLevel.equals("IMMEDIATE"))
			highScore = this.highScore[1];
		else if(stringLevel.equals("EXPERT"))
			highScore = this.highScore[2];
		
		return highScore;
	}
	
	public void setLastPlay(int level, int lastPlay){
		this.lastPlay[level-1] = lastPlay;
	}
	
	public int getLastPlay(int level){
		return this.lastPlay[level-1];
	}
	
	public void showHighScore(UserData userData){
		String strHigh = "";
		
		strHigh = strHigh+"Beginner is "+highScore[0]+"\n";
		strHigh = strHigh+"Immediate is "+highScore[1]+"\n";
		strHigh = strHigh+"Expert is "+highScore[2]+"\n";
		strHigh = strHigh+"-------------------------------------------------\n";
		
		strHigh = strHigh+"Current Level is "+userData.getStringLevel()+"\n";
		strHigh = strHigh+"Last play is "+lastPlay[userData.getLevel()-1]+"\n";
		
		if(userData.getRemainCard() == 0){
			
			strHigh = strHigh+"----------->This play is "+userData.getScore()+"<-----------";
			
			if(userData.getScore() > highScore[userData.getLevel()-1]){
				strHigh = strHigh+"\n***You got new high score***";
				highScore[userData.getLevel()-1] = userData.getScore();
			}
				
			
			//save high score and save last score
			lastPlay[userData.getLevel()-1] = userData.getScore();
			try {
				outFileStream = new FileOutputStream(file);
				outputStream = new DataOutputStream(outFileStream);
							
				for(int i = 0; i < highScore.length; i++){
					
					outputStream.writeInt(highScore[i]);
					outputStream.writeInt(lastPlay[i]);
				}
				outputStream.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
			
		JOptionPane.showMessageDialog(null, strHigh , "High Score!", JOptionPane.PLAIN_MESSAGE);	
	}
}
