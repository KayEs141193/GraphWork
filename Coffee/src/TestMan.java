import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;


public class TestMan extends UntypedActor{
	
	private int counter=0;
	
	public TestMan(){
		
		for(int i=0;i<5;i++)
			getContext().actorOf(Props.create(TestWork.class),Integer.toString(i));
		
		getContext().actorSelection( "../Manager/*").tell("Start", getSelf());
		
	}
	
	@Override
	public void onReceive(Object message){
		
		if(message instanceof String){
			
			counter++;
			
			if(counter==5)
				getContext().system().shutdown();
			
			
		}
		
	}
	
	public static void main(String[] args){
		
		ActorSystem system =ActorSystem.create("TestSystem");
		system.actorOf(Props.create(TestMan.class),"Manager");
		
	}
	
}
