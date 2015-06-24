import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;


public class WriteInFile {

	private Writer w;
	
	public WriteInFile(String file){
		try{
		this.w=new FileWriter(file);
		}
		catch(IOException e){
			
			System.out.println("Couldn't Open the File "+file);
			
		}
	}
	
	public synchronized void write(int src,int dest,double val) throws IOException{
			
			w.write(String.valueOf(src)+" "+String.valueOf(dest)+" "+String.valueOf(val)+"\n");
		
	}

}
