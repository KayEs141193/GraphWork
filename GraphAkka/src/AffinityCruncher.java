import java.util.ArrayList;

import akka.actor.ActorSystem;
import akka.actor.Props;

public class AffinityCruncher {

	public void begin(){
		
		
		ActorSystem crunchSys = ActorSystem.create("AffinityCruncher");
		ArrayList<String> inputFiles= new ArrayList<String>();
		inputFiles.add("/home/kushals/Documents/TestGraphFile/affinity_1/part-r-00000");
		String outputFile="/home/kushals/workspace/GraphAkka/files/outputfile.txt";
		crunchSys.actorOf(Props.create(Distributer.class,inputFiles,outputFile), "distributer");
		
		
	}
	
	public static void main(String[] args){
				
		AffinityCruncher obj=new AffinityCruncher();
		obj.begin();
		
	}
	
	
}
