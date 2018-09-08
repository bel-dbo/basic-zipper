package batch;

import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class CompressTasklet implements Tasklet {
	protected final Logger log = Logger.getLogger(getClass());

	private String source;
	private String destination;
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.info("Compressing " + source + " to " + destination);
	    ZipUtils.compress(source, destination);
		return RepeatStatus.FINISHED;
	}

}
