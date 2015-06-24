import java.io.Serializable;


public class Edge implements Serializable{

	private final int node;
	private final int firstHopAffinity;
	
	public Edge(int node,int firstHopAffinity){
		
		this.node=node;
		this.firstHopAffinity=firstHopAffinity;
		
	}
	
	public int getNode(){
		
		return node;
	}
	
	public int getFirstHopAffinity(){
		
		return firstHopAffinity;
	}
	
}
