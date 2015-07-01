package totalwordcount;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class driver {

	public static Job getJob(String docName,String ipPath,String opPath) throws IOException{
		
		Configuration conf = new Configuration();
		
		
		Job job = Job.getInstance(conf,"Total Word Count");
		
		job.setMapperClass(mapp.class);
		job.setCombinerClass(redd.class);
		job.setReducerClass(redd.class);
		
		FileInputFormat.addInputPath(job, new Path(ipPath));
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		
		job.setMapOutputKeyClass(NullWritable.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		job.setOutputFormatClass(TextOutputFormat.class);
		FileOutputFormat.setOutputPath(job, new Path(opPath));
		
		return job;
		
	}
	
	public static void main(String[] args){
		
		
	}
	
	
}
