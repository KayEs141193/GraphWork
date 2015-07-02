package inverseDocFreq;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class mapp extends Mapper<Text,Text,Text,NullWritable>{

	@Override
	public void map(Text key,Text value,Context context) throws IOException, InterruptedException{
		
		context.write(new Text(key.toString().substring(key.toString().indexOf(' ')+1)), NullWritable.get());
		
	}
	
}
