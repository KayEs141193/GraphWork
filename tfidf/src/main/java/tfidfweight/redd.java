package tfidfweight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class redd extends Reducer<Text,Text,Text,Text>{

	private float idf;
	
	@Override
	public void reduce(Text key,Iterable<Text> values,Context context) throws IOException, InterruptedException{
		
		List<Text> tempVal=new ArrayList<Text>();
		
		for(Text val:values){		
			if(val.toString().indexOf(' ')==-1)
				{idf=Float.parseFloat(val.toString());}
			else
				{tempVal.add(val);}
		}
		//context.write(key, new Text(Float.toString(idf)));
		
//		for(Text val:values)			
//			if(val.toString().indexOf(' ')!=-1)
//				context.write(new Text(val.toString().substring(0,val.toString().indexOf(' '))+" "+key.toString()+" "),new Text(Double.toString(Double.parseDouble(val.toString().substring(val.toString().indexOf(' ')+1))*idf)));

		
		for(Text val:tempVal){
			if(val.toString().indexOf(' ')!=-1)
				context.write(new Text(val.toString().substring(0,val.toString().indexOf(' '))+" "+key.toString()+" "),new Text(Double.toString(Double.parseDouble(val.toString().substring(val.toString().indexOf(' ')+1))*idf)));
		}
		
	}
	
}
