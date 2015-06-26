import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Main {

	public static void main(String[] args) throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException{
		
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf,"Word Count");
		job.setJarByClass(Main.class);
		job.setMapperClass(MapWords.class);
		job.setCombinerClass(ReduceWords.class);
		job.setReducerClass(ReduceWords.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job,new Path("./input.txt"));
		FileOutputFormat.setOutputPath(job, new Path("./output.txt"));
		System.exit(job.waitForCompletion(true)?0:1);
		
		
	}
	
}