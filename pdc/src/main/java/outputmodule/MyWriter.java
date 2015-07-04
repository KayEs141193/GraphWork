package outputmodule;
import java.io.FileWriter;
import java.io.IOException;

public class MyWriter {

	FileWriter fw;
	
	public MyWriter(String opPath) throws IOException{
		
		fw=new FileWriter(opPath);
		
	}
	
	public void write(String record) throws IOException{
		
		fw.write(record+"\n");
		
	}
	
	public void close() throws IOException{
		
		fw.close();
		
	}
	
}
