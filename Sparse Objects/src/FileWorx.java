import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**Class FileWorx is used to interact with text files.
 * 
 * @author kushals
 *
 */

class FileWorx{

	/**initializeGraph is used to initialize graph from text files.
	 * 
	 * @param o This is a GraphObjectSparse that is initially empty and then filled with nodes and edges give in the text file.
	 * @param edgeInfo This is a string specifying the path of the text file from where we read the first hop affinity information to craete the graph.
	 */
	
	public static void initializeGraph(GraphObjectSparse o,String edgeInfo){
		
		System.out.println("Reading from "+edgeInfo);
		try{
			File f=new File(edgeInfo);
			
			Scanner read=new Scanner(f);
			
			while(read.hasNextInt()){
				
				int src=read.nextInt();
				int dest=read.nextInt();
				double val=read.nextDouble();
				try{
					
					o.setEdge(src, dest, val);
					o.setEdge(dest, src, val);
				
				}
				catch(Exception e){
					System.out.println(src+" "+dest+" "+val);
				}
				
				}
			
			read.close();
			
			}
		catch (IOException e){System.out.println(e);}
	
	}
	
	/**writeAffinity calculates affinities for graph o and writes them to the file file.
	 * 
	 * @param o This is a GraphObjectSparse 0 for which we need to calculate the second hop affinities.
	 * @param file This is the name of the text file to which we write down the second hop affinities.
	 */
	
	public static void writeAffinity(GraphObjectSparse o,String file){
		try{
			Writer w=new FileWriter(file);
			
			for (int i=0;i<5;i++){
			
				Set<Integer> possibleNZV=new HashSet<Integer>();
				possibleNZV.addAll(o.getKeySet(i));
				
				for(Integer temp:o.getKeySet(i))
					possibleNZV.addAll(o.getKeySet(temp));
					
				possibleNZV.remove(i);
				
				for(Integer j:possibleNZV){
					
					double affinity=Affinity.CalcAffinity(o,i,j);
					if(affinity>0)
						w.write(String.valueOf(i)+" "+String.valueOf(j)+" "+String.valueOf(affinity)+"\n");
					
				}
				
			}
			w.close();
			
			}
		catch (IOException e){System.out.println(e);}
		
		
	}
	
};