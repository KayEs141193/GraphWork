package totalwordcount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class mapp extends Mapper<Text,Text,NullWritable,IntWritable>{
	
	private IntWritable res=new IntWritable();
	
	@Override
	public void map(Text key,Text value,Context context) throws IOException, InterruptedException{
		
		res.set(Integer.parseInt(value.toString()));
		context.write(NullWritable.get(),res);
		
	}
	
}
