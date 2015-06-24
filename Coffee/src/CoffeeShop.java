import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;


public class CoffeeShop {

	public void start(){
		
		ActorSystem shop = ActorSystem.create("Coffee_Shop");
		
		ActorRef manager = shop.actorOf(Props.create(Manager.class,5));
		
		manager.tell("Capacino",null);
		manager.tell("Latte",null);
		manager.tell("Espresso",null);
		manager.tell("Double Esperesso",null);
		manager.tell("Ice Tea",null);
		manager.tell("Cold Coffee",null);		
		
	}
	
	public static void main(String[] args){
		
		CoffeeShop shop=new CoffeeShop();
		shop.start();
		
	}
	
}
