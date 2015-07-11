package driver;

import inputmodule.CreateExtractor;
import inputmodule.MyExtractor;

import java.io.IOException;
import java.util.Iterator;

import outputmodule.MyWriter;

public class Driver {

	private InputConfReader icr;
	private OutputConfReader ocr;
	private MyWriter mywriter;
	
	public void run() throws IOException{
		
		icr=new InputConfReader();
		ocr=new OutputConfReader();
		
		Iterator<String[]> opConf=ocr.getIterator();
		
		String opPath=opConf.next()[2];
		String[] opFormat=opConf.next();	
		
		String opFormatTemp="";
		
		for(int i=0;i<opFormat.length;i++)
			opFormatTemp+=(","+opFormat[i]);
		
		Iterator<String[]> databases=icr.getIterator();
		
		mywriter=new MyWriter(opPath);
		
		mywriter.write(opFormatTemp.substring(1));
		
		while (databases.hasNext()){
			
			String[] db=databases.next();
			String dbName=db[0],dbType=db[1],dbPath=db[2],mapPath=db[3];
			System.out.println(dbName);
			MyExtractor me = CreateExtractor.get(dbType,dbPath,mapPath,opFormat);
			String record=me.nextRecord();
			
			while(record!=null){
				
				record=me.nextRecord();
				if(record!=null){
					
					mywriter.write(record.replace("\n", ""));
				
				}
			}
			
		}
		
		mywriter.close();
		
		
	}
	
	public static void main(String[] args) throws IOException{

		new Driver().run();
		
	}
	
	
}
