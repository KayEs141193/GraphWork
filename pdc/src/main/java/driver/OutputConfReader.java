package driver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

import com.opencsv.CSVReader;

public class OutputConfReader {
	
	CSVReader reader;
	
	public OutputConfReader() throws FileNotFoundException{
		
		reader = new CSVReader(new FileReader("./opConf.csv"));
		
	}
	
	public Iterator<String[]> getIterator(){
		
		return reader.iterator();
		
	}
	
}
