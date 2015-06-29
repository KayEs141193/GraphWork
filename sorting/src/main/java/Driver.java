import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class Driver {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
		
		Configuration conf=new Configuration();
		Job job = Job.getInstance(conf, "Sort WordCount");
		
		job.setJarByClass(SortedArrayWritable.class);
		job.setNumReduceTasks(1);
		
		job.setMapperClass(SortMap.class);
		job.setReducerClass(SortRed.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(SortedArrayWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		FileInputFormat.addInputPath(job, new Path("hdfs://localhost:9000/kushals/sort/input"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://localhost:9000/kushals/sort/output"));
		
		System.exit(job.waitForCompletion(true)?0:1);
		
	}
	
}
