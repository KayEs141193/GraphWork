import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

/*

Simple Adjacency Matrix Implementation Using SparseArray. 0-based Indexing of the Nodes.
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

write Affinity Time Complexity O(k*N^2) = O(N*E)
k = average degree of the vertex
N = no.of vertices/nodes
E= no.of edges


*/


public class GraphObjectSparse implements Serializable{


	private

	ArrayList<SparseArray> matrix; // O(E) space complexity
	int nodes;

	public
	GraphObjectSparse(int nodes){
		// O(N) implementation as no weight initialization required
		matrix= new ArrayList<SparseArray>(nodes);

		for(int i=0;i<nodes;i++)
			matrix.add(i, new SparseArray());

		this.nodes=nodes;				
	}

	void setEdge(int src, int dest, double val){
		//O(1) time complexity
		matrix.get(src).add(dest, val);
	}

	double getEdge(int src,int dest){
		//O(1) time complexity
		return (matrix.get(src)).get(dest);
	}

	int nodeNo(){
		//O(1) time complexity
		return nodes;
	}

	Set<Integer> getKeySet(int node){

		return matrix.get(node).getKeySet();

	}

};