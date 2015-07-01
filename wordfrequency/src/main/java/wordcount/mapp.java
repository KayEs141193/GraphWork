package wordcount;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class mapp extends Mapper<Object,Text,Text,IntWritable>{

	private IntWritable one= new IntWritable(1);
	private String docName;
	
	@Override
	public void setup(Context context){
		
		docName=context.getConfiguration().get("docName");
		
	}
	
	@Override
	public void map(Object key,Text value,Context context) throws IOException, InterruptedException{
		
		StringTokenizer str = new StringTokenizer(value.toString());
		
		while(str.hasMoreTokens()){
			
			context.write(new Text(docName+" "+str.nextToken()), one);
			
		}
		
	}
	
	
	
}
