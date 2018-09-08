package batch;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import zipper.domain.Stuff;

public class StuffProcessor implements ItemProcessor<Long, Stuff> {
	protected final Logger log = Logger.getLogger(getClass());

	public Stuff process(Long id) throws Exception {
		Stuff stuff = new Stuff();
		stuff.setId(id.toString());
		stuff.setProcessTag(new Date().getTime() + "-" + Thread.currentThread().getId());
		stuff.setDescription("Description of Stuff with ID=" + id.toString());
		stuff.setDetails("These are the details of the Stuff...");
		return stuff;
	}

}
