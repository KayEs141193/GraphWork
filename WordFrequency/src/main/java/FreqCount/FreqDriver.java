package FreqCount;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class FreqDriver {

	public static Job getJob() throws IOException{
		
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "Word Count");
		job.setJar("WordFrequency.jar");
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(FloatWritable.class);
		
		//Setting Mapper Combiner Redcuer Classes
		job.setMapperClass(FreqMap.class);
		
		//Input information
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		FileInputFormat.addInputPath(job, new Path("hdfs://localhost:9000/kushals/wordFreq/wordCountOutput"));
		
		//Output information
		job.setOutputFormatClass(TextOutputFormat.class);
		FileOutputFormat.setOutputPath(job, new Path("hdfs://localhost:9000/kushals/wordFreq/freqCountOutput"));
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FloatWritable.class);
		job.setNumReduceTasks(0);
		
		return job;
		
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException{
		
		getJob().waitForCompletion(true);
		
	}
	
}
