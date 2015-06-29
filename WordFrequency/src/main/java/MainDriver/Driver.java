package MainDriver;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;

import FreqCount.FreqDriver;
import WordCount.CountDriver;

public class Driver {

	public static void main(String[] args) throws IOException{
		
		Configuration conf=	new Configuration();
		
		ControlledJob wordcount = new ControlledJob(conf);
		wordcount.setJob(CountDriver.getJob());
		
		ControlledJob freqcount = new ControlledJob(conf);
		freqcount.setJob(FreqDriver.getJob());
		freqcount.addDependingJob(wordcount);
		
		JobControl jc= new JobControl("Word Frequency");
		jc.addJob(wordcount);
		jc.addJob(freqcount);
		
		jc.run();
		
		System.exit(jc.getFailedJobList().size()==0?0:1);
	}
	
}
