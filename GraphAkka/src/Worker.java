import java.util.HashMap;
import java.util.Map;

import akka.actor.UntypedActor;


public class Worker extends UntypedActor{
	
	private HashMap<Integer,Integer> directEdges;
	private Map<Integer,HashMap<Integer,Integer> > secondaryEdges;
	private int phase=0;
	
	public Worker(){
		
		this.directEdges=new HashMap<Integer,Integer>();
		this.secondaryEdges=new HashMap<Integer,HashMap<Integer,Integer> >();
		
	}
	
	@Override
	public void onReceive(Object message){
		
		if(message instanceof Edge){
			
			directEdges.put(((Edge)message).getNode(),((Edge)message).getFirstHopAffinity());
			
		}
		else if(message instanceof HashMap){
			//((HashMap<Integer,Integer>)message).remove(Integer.parseInt(getSelf().path().name()));
			secondaryEdges.put(Integer.parseInt(getSender().path().name()), ((HashMap<Integer,Integer>)message));
			if(secondaryEdges.size()==directEdges.size()){
				getContext().parent().tell(new NextPhase(), getSelf());
			}
			
		}
		else if(message instanceof RequestEdgeList){
			
			getSender().tell(directEdges, getSelf());
			
		}
		else if(message instanceof NextPhase){
			
			if(phase==0){
				phase++;
				getSender().tell(new NextPhase(), getSelf());
				
			}
			else if(phase==1){

				phase++;
				for(Integer node:directEdges.keySet()){
					
					getContext().actorSelection("../"+Integer.toString(node)).tell(new RequestEdgeList(),getSelf());
					
				}
				
			}
			else if(phase==2){

				//Begin Affinity Crunching
				Map<Integer,Double> finalAffinities=new HashMap<Integer,Double>();
				
				for(Integer node:directEdges.keySet()){
					
					if(finalAffinities.containsKey(node))
						finalAffinities.put(node, finalAffinities.get(node)+(double)directEdges.get(node));
					else
						finalAffinities.put(node, (double)directEdges.get(node));
					
					for(Integer subNode:secondaryEdges.get(node).keySet()){
						
						if(subNode==Integer.parseInt(getSelf().path().name())){
							continue;
						}
						
						if(finalAffinities.containsKey(subNode)){
							
							finalAffinities.put(subNode, finalAffinities.get(subNode)+(double)((double)(directEdges.get(node)*secondaryEdges.get(node).get(subNode))/(double)(directEdges.get(node)+secondaryEdges.get(node).get(subNode))));
							
						}
						else{
							
							finalAffinities.put(subNode,(double)((double)(directEdges.get(node)*secondaryEdges.get(node).get(subNode))/(double)(directEdges.get(node)+secondaryEdges.get(node).get(subNode))));
						}
					}
					
				}
				
				getSender().tell(finalAffinities, getSelf());				
				
			}
			
		}
		else{
			
			unhandled(message);
			
		}
		
	}
	
}
