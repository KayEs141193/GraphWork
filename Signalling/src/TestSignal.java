public class TestSignal {
	
	public static void main(String[] args) throws InterruptedException{
		
		WorkingClass obj=new WorkingClass();
		
		new Thread(new MyThread(obj,1,1),"Thread A").start();
		new Thread(new MyThread(obj,1,2),"Thread B").start();
		Thread.sleep(50);
		new Thread(new MyThread(obj,1,1),"Thread C").start();		
	}
	
}
