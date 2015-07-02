import java.io.IOException;
import org.apache.hadoop.mapreduce.Job;

public class wordFrequencyDriver implements Runnable {

	private boolean JobComplete = false;
	private String docName;
	private String ipPath;
	private String opPath;

	public boolean jobCompleted() {

		return JobComplete;

	}

	private wordFrequencyDriver(String docName, String ipPath, String opPath) {

		this.docName = docName;
		this.ipPath = ipPath;
		this.opPath = opPath;

	}

	public static wordFrequencyDriver getInstance(String docName,
			String ipPath, String opPath) {

		return new wordFrequencyDriver(docName, ipPath, opPath);

	}

	public void run() {

		// Get all path information

		Job wordcountJob;
		try {
			wordcountJob = wordcount.driver.getJob(docName, ipPath, opPath
					+ "/wc");
			if (wordcountJob.waitForCompletion(true)) {
				Job totalwordcountJob = totalwordcount.driver.getJob(docName,
						opPath + "/wc", opPath + "/totwc");

				if (totalwordcountJob.waitForCompletion(true)) {

					Job wordfreqJob = wordfreq.driver.getJob(opPath + "/totwc",
							opPath + "/wc", opPath + "/freqc");

					wordfreqJob.waitForCompletion(true);

					JobComplete = true;

				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
