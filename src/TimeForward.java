
public class TimeForward extends MyTime implements Runnable{
	
	protected Thread clockThread;
	
	public TimeForward(){
		this.setHour(MyTime.MIN_HOUR);
		this.setMinute(MyTime.MIN_MINUTE);
		this.setSecond(MyTime.MIN_SECOND);
		this.start();
	}
	
	public TimeForward(int hour, int minute, int second){
		this.setHour(hour);
		this.setMinute(minute);
		this.setSecond(second);
		this.start();
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
	
			this.increaseOneSecond();
			try{
				Thread.sleep(1000);
			}
			catch(Exception ex){
				
			}
		}
		
	}
	
	public void increaseOneHour(){
		this.hour = this.hour+1;
	}
	
	public void increaseOneMinute(){
		if(this.minute == MyTime.MAX_MINUTE){
			this.minute = MyTime.MIN_MINUTE;
			this.increaseOneHour();
		}
		else
			this.minute = this.minute+1;
	}
	
	public void increaseOneSecond(){
		if(this.second == MyTime.MAX_SECOND){
			this.second = MyTime.MIN_SECOND;
			this.increaseOneMinute();
		}
		else
			this.second = this.second+1;
	}
}
