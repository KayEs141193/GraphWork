package wordcount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class redd extends Reducer<Text,IntWritable,Text,IntWritable>{

	private IntWritable res=new IntWritable();
	
	@Override
	public void setup(Context context){
		
		System.out.println("Reduction Begins.");
		
	}
	
	@Override
	public void reduce(Text key,Iterable<IntWritable> values,Context context) throws IOException, InterruptedException{
		
		int sum=0;
		
		for(IntWritable val:values){
			
			sum+=val.get();
			
		}
		
		res.set(sum);
		
		context.write(key,res);
		
	}
	
}