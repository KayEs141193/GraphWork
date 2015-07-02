package inverseDocFreq;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class redd extends Reducer<Text,NullWritable,Text,Text>{

	private int totalDoc;
	
	@Override
	public void setup(Context context){
		
		totalDoc=Integer.parseInt(context.getConfiguration().get("totalDoc"));
		
	}
	
	@Override
	public void reduce(Text key,Iterable<NullWritable> values,Context context) throws IOException, InterruptedException{
		
		int sum=0;
		
		for(NullWritable nw:values){
			
			sum++;
			
		}
		
		context.write(key,new Text(Double.toString(Math.log((double)totalDoc/sum))));
		
	}
	
}
