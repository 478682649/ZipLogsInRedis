package ZipLogsInRedis.ZipLogsInRedis;

public class StartThread implements Runnable{
	private Long startTime = System.currentTimeMillis();;
	
	private Long endTime = System.currentTimeMillis();;
	
	public static void main(String[] args) {
		for (int i=0 ; i<1 ;i++)
		{
			StartThread oneThread = new StartThread();
			Thread thread = new Thread(oneThread); 
			thread.start(); 
		}

	}
	
	public void run() {
		endTime = System.currentTimeMillis();
		Test test = new Test();
		test.startT(startTime,endTime);
	}

}
