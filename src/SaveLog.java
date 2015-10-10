import java.io.*;
import java.util.Date;
import javax.swing.JOptionPane;

public class SaveLog implements Record{
	private File outFile;
	private FileOutputStream outFileStream;
	private PrintWriter printWriter;
	private Date currentDate;
	
	public SaveLog(){
		currentDate = new Date();
		
		try{
			outFile = new File(FILE_LOG_PATH);
			if(outFile.exists() == true){
				outFileStream = new FileOutputStream(outFile,true);
				printWriter = new PrintWriter(outFileStream,true);
			}
			else{
				outFileStream = new FileOutputStream(outFile,true);
				printWriter = new PrintWriter(outFileStream,true);
				printWriter.println("MyPair's Log");
			}					
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	public SaveLog(String logPath){
		try{
			outFile = new File(logPath);
			if(outFile.exists() == true){
				outFileStream = new FileOutputStream(outFile,true);
				printWriter = new PrintWriter(outFileStream,true);
			}
			else{
				outFileStream = new FileOutputStream(outFile,true);
				printWriter = new PrintWriter(outFileStream,true);
				printWriter.println("MyPair's Log");
			}
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	@Override
	public void recordOpen(){
		currentDate = new Date();
		printWriter.println();
		printWriter.println(currentDate.toLocaleString()+" Program is opened.");
	}
	
	@Override
	public void recordNewGame(){
		currentDate = new Date();
		printWriter.println(currentDate.toLocaleString()+" User new game.");
	}
	
	@Override
	public void recordBeginPlayGame(){
		currentDate = new Date();
		printWriter.println(currentDate.toLocaleString()+" User begin to play MyPair.");
	}
	
	@Override
	public void recordLoadGame(){
		currentDate = new Date();
		printWriter.println(currentDate.toLocaleString()+" User load game.");
	}

	@Override
	public void recordSaveGame(){
		currentDate = new Date();
		printWriter.println(currentDate.toLocaleString()+" User save game.");
	}

	@Override
	public void recordChangeLevel(String currentLevel, String nextLevel){
		currentDate = new Date();
		printWriter.println(currentDate.toLocaleString()+" User change level from "+currentLevel+" to "+nextLevel+".");
	}
	
	@Override
	public void recordChangeDeck(String currentDeck, String nextDeck) {
		currentDate = new Date();
		printWriter.println(currentDate.toLocaleString()+" User change deck from "+currentDeck+" to "+nextDeck+".");
	}
	
	@Override
	public void recordFinishGame(){
		currentDate = new Date();
		printWriter.println(currentDate.toLocaleString()+" User finish game.");
	}

	@Override
	public void recordClose(){
		currentDate = new Date();
		printWriter.println(currentDate.toLocaleString()+" Program is closed.");
		printWriter.close();
	}	
}
