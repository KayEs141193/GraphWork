import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;


public class CoffeeShopWithRouter {

	public void start(){
		
		ActorSystem shop=ActorSystem.create("CoffeeShopRouter");
		
		ActorRef manager=shop.actorOf(Props.create(SmartManager.class,3),"Manager");
		
		manager.tell("Capacino",null);
		manager.tell("Latte",null);
		manager.tell("Espresso",null);
		manager.tell("Double Esperesso",null);
		manager.tell("Ice Tea",null);
		manager.tell("Cold Coffee",null);	
		
	}
	
	public static void main(String[] args){
		
		CoffeeShopWithRouter shop=new CoffeeShopWithRouter();
		shop.start();
		
		
	}
	
}
