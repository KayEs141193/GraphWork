package inputmodule;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;

public class MyCSVParser implements MyParser{

	private CSVReader csvreader;
	
	public MyCSVParser(String dbPath) throws FileNotFoundException{
		
		csvreader= new CSVReader(new FileReader(dbPath));
		
	}
	
	public String[] readNext() throws IOException{
		
		return csvreader.readNext();
		
	}
	
}
