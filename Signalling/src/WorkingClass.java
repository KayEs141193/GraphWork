
public class WorkingClass {

	private MonitorObject obj=new MonitorObject();
	
	public void Wait(long waitTime){
		
		synchronized (obj){
			
			
			System.out.println(Thread.currentThread()+" waits at at "+ System.currentTimeMillis());
			
			try {
				Thread.sleep(10);
				obj.wait(waitTime);
				System.out.println(Thread.currentThread()+" Awakened at "+ System.currentTimeMillis());
				//obj.notify();
				//Thread.sleep(1000);
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void Notify(long waitTime){
		
		synchronized(obj){

			System.out.println(Thread.currentThread()+" sleeps at "+ System.currentTimeMillis());
			try {
				Thread.sleep(1000);
				//obj.wait(waitTime);
			System.out.println(Thread.currentThread()+" notifies at "+ System.currentTimeMillis());
				obj.notify();
				//Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}
	
}
