package batch;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class DirectoryTasklet implements Tasklet {

	protected final Logger log = Logger.getLogger(getClass());
	
	public static final Object CREATE_TMP_DIR = "CREATE_TMP";
	public static final Object DELETE_DIR = "DELETE";
	private String action;
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		if (action.equals(CREATE_TMP_DIR))
			chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("tmpDir", createTmp());
		if (action.equals(DELETE_DIR))
			delete(chunkContext.getStepContext().getJobExecutionContext().get("tmpDir").toString());
			
		return RepeatStatus.FINISHED;
	}
	private void delete(String directory) throws IOException{
		log.info("deleting directory " + directory);
		File dir = new File(directory);
		if (dir.exists())
			FileUtils.deleteDirectory(dir);
	}
	private String createTmp() throws IOException {
		File directory = new File(FileUtils.getTempDirectoryPath() + new Date().getTime());
		FileUtils.forceMkdir(directory);
		log.info("created tmp directory " + directory.getPath() );
		return directory.getPath();
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

}
