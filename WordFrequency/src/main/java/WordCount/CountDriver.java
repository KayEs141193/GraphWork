package WordCount;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class CountDriver {

	public static Job getJob() throws IOException{
		
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "Word Count");
		job.setJar("WordFrequency.jar");
		
		//Setting Mapper Combiner Redcuer Classes
		job.setMapperClass(CountMap.class);
		job.setCombinerClass(CountRed.class);
		job.setReducerClass(CountRed.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		//Input information
		
		job.setInputFormatClass(TextInputFormat.class);
		FileInputFormat.addInputPath(job, new Path("hdfs://localhost:9000/kushals/wordFreq/UserInput"));
		
		//Output information
		job.setOutputFormatClass(TextOutputFormat.class);
		FileOutputFormat.setOutputPath(job, new Path("hdfs://localhost:9000/kushals/wordFreq/wordCountOutput"));
		
		MultipleOutputs.addNamedOutput(job,"total",TextOutputFormat.class,Text.class,IntWritable.class);
		MultipleOutputs.addNamedOutput(job,"wordcount",TextOutputFormat.class,Text.class,IntWritable.class);
		
		return job;
		
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException{
		
		getJob().waitForCompletion(true);
		
	}
	
}
