package inputmodule;

public class CreateParser {

	String parserType;
	
	public MyParser get(String parserType){
		
		MyParser obj;
		
		if(parserType.compareToIgnoreCase("XML")==0)
			obj=new MyXMLParser();
		else
			obj=new MyCSVParser();
		
		return obj;
	}
	
}
