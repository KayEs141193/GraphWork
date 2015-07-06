package inputmodule;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyCSVExtractor implements MyExtractor{

	MyCSVParser csvParser;
	MyMapParser mapParser;
	String[] opFormat=null;
	Map<String,Integer> map=new HashMap<String,Integer>();
	
	public MyCSVExtractor(String dbPath,String mapPath,String[] opFormat) throws IOException{
		
		csvParser = new MyCSVParser(dbPath);
		mapParser = new MyMapParser(mapPath);
		this.opFormat=opFormat;
		
		String[] row=csvParser.readNext();
		
		for(int i=0;i<row.length;i++)
			map.put(row[i], i);
		
	}

	public String nextRecord() throws IOException{
		
		String[] row=null;
		String res="";
		
		if((row=csvParser.readNext())==null)
			return null;
		
		for(String el:opFormat){
			
			if(mapParser.hasKey(el)){
				
				//System.out.println(mapParser.hasKey(el));
				res+=row[map.get(mapParser.getVal(el))];
				
			}
			
			res+=",";
			
		}
		
		return res.substring(0,res.length()-1);
		
	}
	
}
