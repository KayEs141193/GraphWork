import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;


public class Manager extends UntypedActor{
	
	private int availableWorkers;
	private ArrayList<ActorRef> workers;
	private int busy;
	private int ordersCompleted;
	
	public Manager(int availableWorkers){
		
		this.availableWorkers=availableWorkers;
		this.workers= new ArrayList<ActorRef>(availableWorkers);
		for(int i=0;i<availableWorkers;i++){
			ActorRef temp = getContext().actorOf(Props.create(Worker.class),"Worker_"+Integer.toString(i));
			getContext().watch(temp);
			this.workers.add(temp);
			
		}

		this.busy=0;
		this.ordersCompleted=0;
	}
	
	@Override
	public void onReceive(Object message){
		
		if(message instanceof String){
	
			if(busy<availableWorkers){
				
				workers.get(busy).tell(message,getSelf());
				this.busy++;
				
			}else{
				
				System.out.println("No more orders will be taken! Can't make "+message);
				
			}
			
		}
		
		if(message instanceof Order){
			
			System.out.println("Serving "+((Order) message).serve());
			this.ordersCompleted++;
			
			if(ordersCompleted==availableWorkers){
				
				System.out.println("Closing the Shop.");
				getContext().system().shutdown();
				
			}
			
		}
		if(message instanceof Terminated){
			
			System.out.println(((Terminated)message).getActor() + " terminated.");
			
		}
		else{
			
			unhandled(message);
		}
		
	}
	
	
}
