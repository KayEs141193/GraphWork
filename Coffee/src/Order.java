import java.io.Serializable;


public class Order implements Serializable{

	private String name;
	
	public Order(String name){
		
		this.name=name;
		
	}
	
	public String serve(){
		
		return name;
		
	}
	
}
