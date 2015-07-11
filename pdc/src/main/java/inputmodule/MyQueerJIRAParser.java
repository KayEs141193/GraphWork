package inputmodule;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;

public class MyQueerJIRAParser implements MyParser{

	private CSVReader csvreader;
	
	public MyQueerJIRAParser(String dbPath) throws FileNotFoundException{
		
		csvreader= new CSVReader(new FileReader(dbPath));
		
	}
	
	public String[] readNext() throws IOException{
		
		return csvreader.readNext();
		
	}
	
	public void close() throws IOException{
		
		csvreader.close();
		
	}
	
}
