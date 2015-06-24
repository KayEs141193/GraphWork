import java.util.Random;

import akka.actor.Terminated;
import akka.actor.UntypedActor;


public class Worker extends UntypedActor{

	@Override
	public void onReceive(Object message){
		
		if(message instanceof String){
			
			System.out.println(self().path().name()+" making "+message);
			Random randomGenerator = new Random();
			int timeToMake = randomGenerator.nextInt(1000);
			try {

				Thread.sleep(timeToMake);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(self().path().name()+" made "+message+" in "+timeToMake);
			
			getSender().tell(new Order((String)message), getSelf());
		}
		else{
			
			unhandled(message);
			
		}

	}
	
}
