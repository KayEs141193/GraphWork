
public class MyThreads implements Runnable{

	private String name;
	private int time;
	
	public MyThreads(String name,int time){
		
		this.name=name;
		this.time=time;
	}
	
	@Override
	public void run(){
		
		System.out.println(name+" going to sleep!");
		try {
			Thread.sleep(this.time*1000);
			System.out.println(name+" is awake Now!");
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(name+" can't sleep :(");
			e.printStackTrace();
		}
		
	}
	
}
