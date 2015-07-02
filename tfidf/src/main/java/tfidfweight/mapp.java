package tfidfweight;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class mapp extends Mapper<Text,Text,Text,Text>{

	@Override
	public void map(Text key,Text value,Context context) throws IOException, InterruptedException{
		
		if(key.toString().indexOf(' ')==-1){
			
			context.write(key,value);
			
		}
		else{
			
			context.write(new Text(key.toString().substring(key.toString().indexOf(' ')+1)),new Text(key.toString().substring(0,key.toString().indexOf(' '))+" "+value.toString()));
			
		}
		
	}
	
}
