
public class TimeBackward extends MyTime implements Runnable{
	
	protected Thread clockThread;
	
	public TimeBackward(){
		this.setHour(MyTime.MIN_HOUR);
		this.setMinute(MyTime.MIN_MINUTE);
		this.setSecond(MyTime.MIN_SECOND);
	}
	
	public TimeBackward(int hour, int minute, int second){
		this.setHour(hour);
		this.setMinute(minute);
		this.setSecond(second);
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
	public void run() {
		while(true){
			if(this.second == MIN_SECOND)
				this.stop();
			else
				this.decreaseOneSecond();			
			try{
				Thread.sleep(1000);
			}
			catch(Exception ex){
				
			}
		}
		
	}
	
	public void decreaseOneHour(){
		this.hour = this.hour-1;
	}
	
	public void decreaseOneMinute(){
		if(this.minute == MIN_MINUTE){
			this.minute = MAX_MINUTE;
			this.decreaseOneHour();
		}
		else
			this.minute = this.minute-1;
	}
	
	public void decreaseOneSecond(){
		if(this.second == MIN_SECOND){
			this.second = MAX_SECOND;
			this.decreaseOneMinute();
		}
		else
			this.second = this.second-1;
	}
}
