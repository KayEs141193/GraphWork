package FreqCount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


	public class FreqMap extends Mapper<Text,Text,Text,FloatWritable>{
	
		private static IntWritable totalWordCount=new IntWritable(0);
		private static FloatWritable freq=new FloatWritable();
		
		@Override
		public void setup(Context context) throws IOException{
			
			Path pt=new Path("hdfs://localhost:9000/kushals/wordFreq/wordCountOutput/total-m-00000");
			FileSystem fs= FileSystem.get(new Configuration());
			BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(pt)));
			String line= br.readLine();
			totalWordCount.set(Integer.parseInt(line.substring(6)));
			
		}
		
		@Override
		public void map(Text key,Text value,Context context) throws IOException, InterruptedException{
		
			freq.set((float)Integer.parseInt(value.toString())/totalWordCount.get());
			context.write(key,freq);
			
		}
	
	}

