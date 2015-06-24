import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;


public class Distributer extends UntypedActor {

	private Writer writer;
	private Set<Integer> jobsToComplete;
	private int jobsCompleted;
	
	public Distributer(ArrayList<String> inputFiles, String outputFile) throws IOException{
		
		System.out.println(System.currentTimeMillis());
		
		this.writer = new FileWriter(outputFile);
		this.jobsCompleted=0;
		this.jobsToComplete=new HashSet<Integer>();
		
		for(String file:inputFiles){
			
			File inputFile = new File(file);
			
			try {
				Scanner reader=new Scanner(inputFile);
					
				while(reader.hasNextInt()){
					
					int workerNumber=reader.nextInt();
					int otherNode=reader.nextInt();
					int firstHopAffinity=reader.nextInt();
					if(!jobsToComplete.contains(workerNumber)){
						
						jobsToComplete.add(workerNumber);
						getContext().actorOf(Props.create(Worker.class),Integer.toString(workerNumber));
						//System.out.println(temp.path().name());
					}
					

					getContext().actorSelection(Integer.toString(workerNumber)).tell(new Edge(otherNode,firstHopAffinity), getSelf());
					
					if(!jobsToComplete.contains(otherNode)){
						
						jobsToComplete.add(otherNode);
						getContext().actorOf(Props.create(Worker.class),Integer.toString(otherNode));
						//System.out.println(temp.path().name());
					}
					
					getContext().actorSelection(Integer.toString(otherNode)).tell(new Edge(workerNumber,firstHopAffinity), getSelf());
					
				}
				
				
				reader.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		//Broadcast the message to begin the affinityCrunching
		getContext().actorSelection( "../distributer/*").tell(new NextPhase(), getSelf());
		
	}
	
	@Override
	public void onReceive(Object message) throws IOException{
		
		if(message instanceof NextPhase){
			
			jobsCompleted++;
			
			if(jobsCompleted==jobsToComplete.size()){
				
					jobsCompleted=0;					
					getContext().actorSelection( "../distributer/*").tell(new NextPhase(), getSelf());
				
			}
			
			
		}
		else if (message instanceof HashMap){
			
			//Write information to output file
			jobsCompleted++;
			try {
				
				for(Integer node:((HashMap<Integer,Integer>) message).keySet()){
					
						//System.out.println(getSender().path().name()+" "+Integer.toString(node)+" "+((HashMap<Integer,Integer>)message).get(node));
						writer.write(getSender().path().name()+" "+Integer.toString(node)+" "+((HashMap<Integer,Integer>)message).get(node)+"\n");
					
				}

				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//getSender().tell(PoisonPill.getInstance(), getSelf());
			
			if(jobsCompleted==jobsToComplete.size()){
				
				System.out.println(System.currentTimeMillis());
				writer.close();
				getContext().system().shutdown();
				
			}
			
		}
		else{
			
			unhandled(message);
			
		}
		
		
	}
	
}
