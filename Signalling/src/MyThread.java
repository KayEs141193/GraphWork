
public class MyThread implements Runnable{

	private WorkingClass obj;
	private long waitTime;
	private int work;
	
	public MyThread(WorkingClass obj,long waitTime,int work){
		
		this.obj=obj;
		this.waitTime=waitTime;
		this.work=work;
	}
	
	@Override
	public void run(){
		
		if(work==1)
			obj.Wait(waitTime);
		else
			obj.Notify(waitTime);
	}
	
	
}
