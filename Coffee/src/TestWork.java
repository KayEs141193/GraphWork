import akka.actor.UntypedActor;


public class TestWork extends UntypedActor{

	
	@Override
	public void onReceive(Object message){
	
		System.out.println("Hey I am "+getSelf().path().name());
		getSender().tell("Done", getSelf());
		
	}
	
	
}
