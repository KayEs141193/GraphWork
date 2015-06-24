import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class FileWorx{

	public static void initializeGraph(GraphObjectSparse o,String edgeInfo){
	// O(E) time complexity	
		
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
	
	public static void writeAffinity(GraphObjectSparse o,String file){
		try{
			Writer w=new FileWriter(file);
			
			for (int i=0;i<5;i++){
			
				Set<Integer> possibleNZV=new HashSet<Integer>();//Set of all nodes that can have non zero value for the current source
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