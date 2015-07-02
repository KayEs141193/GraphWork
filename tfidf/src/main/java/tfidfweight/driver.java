package tfidfweight;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class driver {

	public static Job getJob(List<String> ipPaths,String opPath) throws IOException{
		
		Configuration conf = new Configuration();
		
		Job job=Job.getInstance(conf,"TFIDF Job");
		job.setJarByClass(driver.class);
		
		for(String path:ipPaths)
			FileInputFormat.addInputPath(job,new Path(path));
		
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		
		job.setMapperClass(mapp.class);
		job.setReducerClass(redd.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileOutputFormat.setOutputPath(job,new Path(opPath) );
		
		return job;
	}
	
	public static void main(String[] args){
		
		
		
	}
	
}
