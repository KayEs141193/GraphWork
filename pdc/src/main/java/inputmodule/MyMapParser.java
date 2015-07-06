package inputmodule;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;

public class MyMapParser {

	private Map<String,String> map=new HashMap<String,String>();
	
	public MyMapParser(String mapPath) throws IOException{
		
		CSVReader mapparser=new CSVReader(new FileReader(mapPath));
		
		List<String[]> itr = mapparser.readAll();
		
		for(String[] i:itr){
			
			if(i.length==2){
				if(i[1].length()>0)
					map.put(i[0], i[1]);
				
			}
			
		}	
		
		mapparser.close();
		
	}
	
	public boolean hasKey(String key){
		
		return map.containsKey(key);
		
	}
	
	public String getVal(String key){
		
		return map.get(key);
		
	}
	
}