import java.util.ArrayList;
import java.util.Collections;

/*

Simple Adjacency Matrix Implementation. 0-based Indexing of the Nodes.
Naive Implementation for Affinity Calculation. Doesn't divide the graph into smaller sub graphs.
Input File format: node1 node2 direct_affinity
Output File format: node1 node2 effective_affinity

Thought:
O(kE+V) implementation of the sub-graph division using edge-major approach.
k: average degree of the vertices E: no.of edges V: no.of vertices
Uses a sparse vector.
Explanation: Form a pointer based graph structure using sparse vector to store the edge connectivity

To Do:
Decide whether to use sparse data structures on the basis of average out-degree. Implement.
Discuss about the current graph implementation in Java
Efficient Division into sub-graph O(E+V)


*/

class GraphObject{
	
	private
		ArrayList<ArrayList<Double>> matrix; // O(N^2) space complexity
		int nodes;
	public
		GraphObject(int nodes){
		// O(N^2) implementation as all the weights are being initialized to 0
			matrix= new ArrayList<ArrayList<Double>>(nodes);
			this.nodes=nodes;
			
			for(int i=0;i<nodes;i++){
				
				matrix.add(i,new ArrayList<Double>(Collections.nCopies(nodes,(0.0))));
			
			}	
		}
	
		void setEdge(int src, int dest, double val){
			//O(1) time complexity
			matrix.get(src).set(dest, val);}

		double edgeVal(int src,int dest){
			//O(1) time complexity
			return matrix.get(src).get(dest);
		}
		
		int nodeNo(){
			//O(1) time complexity
			return nodes;
		}

};