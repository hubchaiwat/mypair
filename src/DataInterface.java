
public interface DataInterface {
	
	public String FILE_SAVE_PATH = "data/savefile.mps";
	public String FILE_SETTING_PATH = "data/setting.mps";
	
	public boolean checkHaveData();
	public boolean loadGameSetting(UserData userData);
	public boolean loadGameData(Card[] card, TimeForward playTime, UserData userData);
	public boolean saveGameData(TimeForward playTime, UserData userData, Card[] card);
	public boolean changeLevelData(UserData userData);
	public boolean changeDeckData(UserData userData);
}
