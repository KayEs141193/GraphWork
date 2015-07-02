import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class tfidfDriver {

	public static void main(String[] args) throws ClassNotFoundException,
			IOException, InterruptedException {

		String basePath = "hdfs://localhost:9000/kushals/tfidf/";

		List<String> ipPaths = new ArrayList<String>();
		ipPaths.add(basePath);
		ipPaths.add(basePath);
		ipPaths.add(basePath);
		ipPaths.add(basePath);
		List<String> ipTemp = new ArrayList<String>();

		// Word Frequency Count Jobs

		ThreadGroup termFreqJobs = new ThreadGroup("Word Frequency Thread");

		int jobNo = 1;
		for (String path : ipPaths) {
			new Thread(termFreqJobs, wordFrequencyDriver.getInstance(
					("doc" + Integer.toString(jobNo)),
					path + ("doc" + Integer.toString(jobNo)), basePath
							+ "wfop_doc" + Integer.toString(jobNo))).start();
			jobNo++;
		}
		
		while (termFreqJobs.activeCount() != 1);
		// Wait till all word frequency jobs have been completed

		termFreqJobs.list();
		
		jobNo = 1;
		for (String path : ipPaths)
			ipTemp.add(path + "wfop_doc" + Integer.toString(jobNo++) + "/freqc");

		// Document Inverse Jobs
		inverseDocFreq.driver.getJob(ipTemp, basePath + "idf/")
				.waitForCompletion(true);

		ipTemp.add(basePath + "idf");
		tfidfweight.driver.getJob(ipTemp, basePath + "tfidf/")
				.waitForCompletion(true);

	}

}