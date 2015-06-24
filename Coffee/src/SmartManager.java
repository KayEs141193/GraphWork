import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;


public class SmartManager extends UntypedActor{

	private ActorRef router;
	private int jobsToComplete;
	private int jobsCompleted;
	
	public SmartManager(int availableWorkers){
		
		
		router=getContext().actorOf(new RoundRobinPool(availableWorkers).props(Props.create(Worker.class)) , "Router");
		getContext().watch(router);
		this.jobsToComplete=0;
		this.jobsCompleted=0;
		
	}
	
	@Override
	public void onReceive(Object message){
		
		if(message instanceof String){
			
			this.jobsToComplete++;
			router.tell(message,getSelf());
				
		}
		
		else if(message instanceof Order){
			
			this.jobsCompleted++;
			System.out.println("Serving "+((Order) message).serve());
			
			if(this.jobsCompleted==this.jobsToComplete){

				System.out.println("Closing the shop!! ");
				this.getContext().system().shutdown();
				
				
			}
			
		}
		else if(message instanceof Terminated){
			
			System.out.println("Closing the shop!! ");
			this.getContext().system().shutdown();
			
		}
		
		else{
			
			unhandled(message);
		}
		
	}
	
}
