package batch;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"/job.xml","/test-context.xml"})
public class ArchiveStuffIntegrationTest {
	
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job;
	
	@Test
	public void archiveStuff() throws Exception {
		jobLauncher.run(job, new JobParametersBuilder()
				.addString("tmpDirectory", "./target/tmp/")
				.addLong("timestamp",System.currentTimeMillis())
				.toJobParameters());
	}
}
