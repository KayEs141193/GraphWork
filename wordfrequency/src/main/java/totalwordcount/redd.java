package totalwordcount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class redd extends Reducer<NullWritable,IntWritable,Text,Text>{

	private IntWritable res=new IntWritable();
	
	@Override
	public void reduce(NullWritable key,Iterable<IntWritable> values,Context context) throws IOException, InterruptedException{
		
		int sum=0;
		
		for(IntWritable val:values){
			
			sum+=val.get();
			
		}
		
		res.set(sum);
		
		context.write(new Text("Total"),new Text(res.toString()));
		
	}
	
}
