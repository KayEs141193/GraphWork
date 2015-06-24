
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

public class WriteThread implements Runnable{
	
	private GraphObjectSparse o;
	private int start;
	private WriteInFile file;
	
	public WriteThread(GraphObjectSparse o,int node,WriteInFile file){
		
		this.o=o;
		this.start=node;
		this.file=file;
	}
	
	private void write(){
		try{
			for(int i=0;i<6019;i++)
			{	int node=start+i;
				Set<Integer> possibleNZV=new HashSet<Integer>();//Set of all nodes that can have non zero value for the current source
				possibleNZV.addAll(o.getKeySet(node));
				
				for(Integer temp:o.getKeySet(node))
					possibleNZV.addAll(o.getKeySet(temp));
					
				possibleNZV.remove(node);
				
				for(Integer j:possibleNZV){
					
					double affinity=Affinity.CalcAffinity(o,node,j);
					if(affinity>0){
						
						this.file.write(node,j,affinity);

					}
					
				}
				
			}
			
			}
		catch (IOException e){System.out.println(e);}
		
		
	}
	
	@Override
	public void run(){
		
		long begTime = System.currentTimeMillis( );
		this.write();
		long endTime = System.currentTimeMillis( );
		System.out.println("Time required to write batch from "+start+" ="+Long.toString(endTime-begTime)+" ms");
		GregorianCalendar gcalendar = new GregorianCalendar();
	      System.out.print("Time: ");
	      System.out.print(gcalendar.get(Calendar.HOUR) + ":");
	      System.out.print(gcalendar.get(Calendar.MINUTE) + ":");
	      System.out.println(gcalendar.get(Calendar.SECOND));

	}
	
}
