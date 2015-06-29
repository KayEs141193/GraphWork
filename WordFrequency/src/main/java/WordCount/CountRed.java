package WordCount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

public class CountRed extends Reducer<Text,IntWritable,Text,IntWritable>{

	private IntWritable res = new IntWritable();
	private MultipleOutputs<Text,IntWritable> mos;
	
	@Override
	public void setup(Context context){
		
		mos= new MultipleOutputs(context);
		
	}
	
	@Override
	public void reduce(Text key,Iterable <IntWritable> values,Context context) throws IOException, InterruptedException{
		
		int sum=0;
		
		for(IntWritable val:values){
			
			sum+=val.get();
			
		}
		
		res.set(sum);
		if(key.toString().equalsIgnoreCase("total"))
			mos.write("total",key, res);
		else
			mos.write("wordcount", key, res);
		
	}
	
	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException{
		
		mos.close();
		
	}
	
}
