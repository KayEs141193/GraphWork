import java.io.IOException;

import org.apache.hadoop.mapreduce.Job;


public class wordFrequencyDriver{
	
	public void run(String docName,String ipPath,String opPath) throws IOException, ClassNotFoundException, InterruptedException{
		
		//Get all path information
		
		Job wordcountJob=wordcount.driver.getJob(docName, ipPath, opPath);
		
		if(wordcountJob.waitForCompletion(true)){
			
			Job totalwordcountJob=totalwordcount.driver.getJob(docName, ipPath, opPath);
			
			if(totalwordcountJob.waitForCompletion(true)){
				
				String totalPath=""; //SET THIS
				
				Job wordfreqJob=wordfreq.driver.getJob(totalPath, ipPath, opPath);
				
				wordfreqJob.waitForCompletion(true);
				
			}
			
			
		}
		
		
	}
	
}
