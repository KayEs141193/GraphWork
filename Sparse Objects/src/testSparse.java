
class testSparse{

	
	public static void main(String args[]){
		
		//int nodes=385254;
		int nodes=5;
		GraphObjectSparse o= new GraphObjectSparse(nodes);
		//String inputfile="/home/kushals/Documents/TestGraphFile/affinity_1/part-r-00000";
		String inputfile="/home/kushals/workspace/Sparse Objects/files/input.txt";
		String outputfile="/home/kushals/workspace/Sparse Objects/files/output.txt";

		FileWorx.initializeGraph(o, inputfile);

		FileWorx.writeAffinity(o, outputfile);

	}	
}