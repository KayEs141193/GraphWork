import java.util.Random;


public class testThread {

	public static void main(String args[]){
		
		Random generator=new Random();
		
		for(int i=0;i<5;i++){
			
			int sleepTime=generator.nextInt(5);
			System.out.println(sleepTime);
			Thread temp=new Thread(new MyThreads(Integer.toString(i),sleepTime));
			temp.start();
			
		}
		
		
	}
	
}
