package driver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

import com.opencsv.CSVReader;

public class InputConfReader {

	private CSVReader reader;
	
	public InputConfReader() throws FileNotFoundException{
		
		reader = new CSVReader(new FileReader("./ipConf.csv"));
		
	}
	
	public Iterator<String[]> getIterator(){
		
		return reader.iterator();
		
	}
	
}