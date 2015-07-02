package wordfreq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class mapp extends Mapper<Text,Text,Text,Text>{
	
	private int total;
	
	@Override
	public void setup(Context context) throws IOException{
		
		Path pt=new Path(context.getConfiguration().get("totalPath")+"/part-r-00000");
		FileSystem fs= FileSystem.get(new Configuration());
		BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(pt)));	
		total=Integer.parseInt(br.readLine());
		br.close();
	}
	
	@Override
	public void map(Text key,Text value,Context context) throws IOException, InterruptedException{
			
			context.write(key, new Text(Float.toString((float)Integer.parseInt(value.toString())/total)));
					
	}
	
	
	
}
