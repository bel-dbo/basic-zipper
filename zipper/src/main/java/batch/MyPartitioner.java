package batch;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

public class MyPartitioner implements Partitioner {
	protected final Logger log = Logger.getLogger(getClass());

	private static final String PARTITION_KEY = "partition";
	private static final Integer TOTAL_DATA_SIZE = 2000;

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		log.info("partitioning..." + gridSize);

		Map<String, ExecutionContext> map = new HashMap<String, ExecutionContext>(gridSize);
		int range = TOTAL_DATA_SIZE / gridSize;
		int start = 0;
		int end = start + range - 1;
		int number = 0;
		while (start < TOTAL_DATA_SIZE) {
			if (end >= TOTAL_DATA_SIZE)
				end = TOTAL_DATA_SIZE;
			ExecutionContext ctx = new ExecutionContext();
			ctx.put("fileName", "index." + (number) + ".meta");
			ctx.put("listIndex", number);
			ctx.put("minIndex", start);
			ctx.put("maxIndex", end);
			log.info("partition " + start + " to " + end);
			map.put(PARTITION_KEY + number, ctx);
			number++;
			start += range;
			end += range;
		}
		return map;
	}

}
