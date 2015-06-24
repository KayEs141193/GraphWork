import java.util.Set;

class Affinity{
	
	public static double CalcAffinity(GraphObjectSparse o,int src,int dest){
		//O(N) time complexity
		double effectiveAffinity=0;
		Set<Integer> adjacentToSource=o.getKeySet(src);
		
		for(Integer i:adjacentToSource){
			//Iterating over all nodes to which src node maybe connected to
			
			if(o.getEdge(src, i)>0 && o.getEdge(i, dest)>0){
				
				effectiveAffinity+=(o.getEdge(i, dest)*o.getEdge(src, i)/(o.getEdge(i, dest)+o.getEdge(src, i)));
				
			}
			
			
		}
		
		effectiveAffinity+=o.getEdge(src,dest);
		
		return effectiveAffinity;
		
	}
	
	
};
