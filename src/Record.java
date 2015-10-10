
public interface Record {
	public String FILE_LOG_PATH = "data/log.txt";
	
	public void recordOpen();
	public void recordNewGame();
	public void recordBeginPlayGame();
	public void recordLoadGame();
	public void recordSaveGame();
	public void recordChangeLevel(String currentLevel, String nextLevel);
	public void recordChangeDeck(String currentDeck, String nextDeck);
	public void recordFinishGame();
	public void recordClose();
}
