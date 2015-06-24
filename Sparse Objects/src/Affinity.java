import java.util.Set;

/**Affinity class basically calculates second hop affinity for the specified pair of source and destination nodes for a give graph.
 * 
 * @author kushals
 * 
 */

class Affinity{
	
	/**
	 * 
	 * @param o This is a GraphObjectSparse instance. This is basically the which is used for affinity calculation between the specified nodes.
	 * @param src This integer values specifies the source node.
	 * @param dest This integer value specifies the destination node.
	 * @return The function returns a double value, which is the affinity between the src and dest nodes of the graph o.
	 */
	
	public static double CalcAffinity(GraphObjectSparse o,int src,int dest){
		
		double effectiveAffinity=0;
		Set<Integer> adjacentToSource=o.getKeySet(src);
		
		for(Integer i:adjacentToSource){
			
			if(o.getEdge(src, i)>0 && o.getEdge(i, dest)>0){
				
				effectiveAffinity+=(o.getEdge(i, dest)*o.getEdge(src, i)/(o.getEdge(i, dest)+o.getEdge(src, i)));
				
			}
			
			
		}
		
		effectiveAffinity+=o.getEdge(src,dest);
		
		return effectiveAffinity;
		
	}
	
	
};
