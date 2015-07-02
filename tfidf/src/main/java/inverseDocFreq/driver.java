package inverseDocFreq;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class driver {

	public static Job getJob(List<String> pathToFreq,String opPath) throws IOException{
		
		Configuration conf=new Configuration();
		conf.set("totalDoc", Integer.toString(pathToFreq.size()));
		Job job=Job.getInstance(conf,"IDF");
		job.setJarByClass(driver.class);
		
		for(String str:pathToFreq){
			
			FileInputFormat.addInputPath(job, new Path(str));
			
		}
		
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		
		job.setMapperClass(mapp.class);
		job.setReducerClass(redd.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(NullWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		FileOutputFormat.setOutputPath(job, new Path(opPath));
		
		return job;
	}
	
}