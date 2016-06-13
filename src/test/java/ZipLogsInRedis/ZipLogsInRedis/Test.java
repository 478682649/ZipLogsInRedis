package ZipLogsInRedis.ZipLogsInRedis;

import java.util.Timer;
import java.util.TimerTask;

public class Test extends  TimerTask{
	
	private static Long startTime ;
	
	private static Long endTime ;

	private static Integer num = 0 ;
	
	public static void main(String[] args) {
		

		
		Long delay = (long) 1;
		Long period = (long) 2;
		TimerTask  task = new Test();  
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task,delay*1000, period*1000);

	}

	@Override
	public void run() {
		//System.out.println(new Date());
		
		while(true)
		{
			System.out.println(Thread.currentThread().getName());
			System.out.println(startTime);
			System.out.println(endTime);
			System.out.println("---------------------");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
public  void startT(long starttime ,long endtime) {
	this.startTime = starttime;
	this.endTime = endtime;
			
		Long delay = (long) 1;
		Long period = (long) 2;
		TimerTask  task = new Test();  
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task,delay*1000, period*1000);
System.out.println(startTime);
System.out.println(endTime);
System.out.println("---------------------");
	}

}
