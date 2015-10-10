
public abstract class MyTime implements Time{
		
	protected int hour, minute, second; 	
	
	public MyTime(){
		this.setHour(MyTime.MIN_HOUR);
		this.setMinute(MyTime.MIN_MINUTE);
		this.setSecond(MyTime.MIN_SECOND);
	}
	
	public MyTime(int hour, int minute, int second){
		this.setHour(hour);
		this.setMinute(minute);
		this.setSecond(second);
	}

	public void setHour(int hour){
		this.hour = hour;
	}
	
	public int getHour(){
		return this.hour;
	}
	
	public void setMinute(int minute){
		this.minute = minute;
	}
	
	public int getMinute(){
		return this.minute;
	}
	
	public void setSecond(int second){
		this.second = second;
	}
	
	public int getSecond(){
		return this.second;
	}
	
	@Override
	public String getStringTime(){
		String strTime = "";
		
		//Add hour to strTime
		if(this.getHour() < MyTime.BASE_DIGIT)
			strTime = strTime+"0"+this.hour+" : ";
		else
			strTime = strTime+this.hour+" : ";
			
		//Add minute to strTime
		if(this.getMinute() < MyTime.BASE_DIGIT)
			strTime = strTime+"0"+this.minute+" : ";		
		else
			strTime = strTime+this.minute+" : ";
		
		//Add second to strTime
		if(this.getSecond() < MyTime.BASE_DIGIT)
			strTime = strTime+"0"+this.second;
		else
			strTime = strTime+this.second;
		
		return strTime;
	} 	
}
