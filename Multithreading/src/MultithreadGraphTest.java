import java.util.Calendar;
import java.util.GregorianCalendar;

public class MultithreadGraphTest {
	
	public static void main(String[] args){
		
		long begTime = System.currentTimeMillis( );
		GregorianCalendar gcalendar = new GregorianCalendar();
	      System.out.print("Time: ");
	      System.out.print(gcalendar.get(Calendar.HOUR) + ":");
	      System.out.print(gcalendar.get(Calendar.MINUTE) + ":");
	      System.out.println(gcalendar.get(Calendar.SECOND));
		int nodes=385254;
		GraphObjectSparse o= new GraphObjectSparse(nodes);
		String inputfile1="/home/kushals/Documents/TestGraphFile/affinity_1/part-r-00000";
		String inputfile2="/home/kushals/Documents/TestGraphFile/affinity_1/part-r-00001";
		String outputfile="/home/kushals/workspace/Multithreading/files/outputv2.txt";
		FileWorx.initializeGraph(o, inputfile1);
		FileWorx.initializeGraph(o, inputfile2);
		long endTime = System.currentTimeMillis( );
		System.out.println("Time for graph initialization = "+Long.toString(endTime-begTime)+" ms");
		
	    // Get the Java runtime
//	    Runtime runtime = Runtime.getRuntime();
//	    // Calculate the used memory
//	    long memory = runtime.totalMemory() - runtime.freeMemory();
//		System.out.println("Memory used only for graph: "+memory)	;
//		WriteInFile war[]={new WriteInFile(outputfile+"1.txt"),new WriteInFile(outputfile+"2.txt"),
//		new WriteInFile(outputfile+"3.txt"),new WriteInFile(outputfile+"4.txt")};
		WriteInFile w=new WriteInFile(outputfile);
		
		for(int i=0;i<64;i++){

			new Thread(new WriteThread(o,i*6019,w)).start();
		}
		
	}

}