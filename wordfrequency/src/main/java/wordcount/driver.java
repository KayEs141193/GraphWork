package wordcount;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class driver {

	public static Job getJob(String docName,String ipPath,String opPath) throws IOException{
		
		Configuration conf = new Configuration();
		
		conf.set("docName", docName);
		
		Job job = Job.getInstance(conf,"Word Count");
		job.setJarByClass(driver.class);
		
		job.setMapperClass(mapp.class);
		job.setCombinerClass(redd.class);
		job.setReducerClass(redd.class);
		
		FileInputFormat.addInputPath(job, new Path(ipPath));
		job.setInputFormatClass(TextInputFormat.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setOutputFormatClass(TextOutputFormat.class);
		FileOutputFormat.setOutputPath(job, new Path(opPath));
		
		return job;
		
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException{
		
		getJob("doc1","hdfs://localhost:9000/kushals/tfidf/doc1.txt","hdfs://localhost:9000/kushals/tfidf/tempop").waitForCompletion(true);
		
	}
	
	
}
