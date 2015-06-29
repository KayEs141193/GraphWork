package WordCount;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CountMap extends Mapper<Object,Text,Text,IntWritable>{

	private IntWritable one=new IntWritable(1);
	private Text wrd=new Text();
	
	public void map(Object key,Text value,Context context) throws IOException, InterruptedException
	{
		StringTokenizer str=new StringTokenizer(value.toString());

		while(str.hasMoreTokens()){
			
			wrd.set(str.nextToken());
			context.write(wrd, one);
			wrd.set("total");
			context.write(wrd,one);
			
		}
		
	}
	
	
}
