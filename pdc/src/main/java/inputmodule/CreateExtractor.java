package inputmodule;

import java.io.IOException;

public class CreateExtractor {

	public static MyExtractor get(String dbType,String dbPath,String mapPath,String[] opFormat) throws IOException{
		
		MyExtractor obj;
		
		if(dbType.compareToIgnoreCase("JIRA")==0)
			obj=new MyQueerJIRAExtractor(dbPath,mapPath,opFormat);
		else
			obj=new MyCSVExtractor(dbPath,mapPath,opFormat);
		
		return obj;
	
	}
	
}
