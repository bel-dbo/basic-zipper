package batch;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemWriter;

import zipper.domain.Stuff;

public class StuffWriter implements ItemWriter<Stuff> {
	protected final Logger log = Logger.getLogger(getClass());

	private String directory;
	public void write(List<? extends Stuff> items) throws Exception {
		log.info("writing " + items.size() + " stuff(s) to archive (" + directory +")");
		File dir = new File(directory);
		if (!dir.exists()) {
			FileUtils.forceMkdir(dir);
		}
		for (Stuff stuff: items) {
			FileOutputStream fos = new FileOutputStream(directory + "stuff-" + stuff.getId() + ".zip");
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			ZipOutputStream zos = new ZipOutputStream(bos);
			try {
				zos.putNextEntry(new ZipEntry("stuff-" + stuff.getId() + ".txt"));
				zos.write(write(stuff).getBytes());
				//log.info("writing " + stuff.toString());
				zos.closeEntry();
			}
			finally {
				zos.close();
			}
		}
	}
	
	private String write(Stuff stuff) {
		StringBuilder sb = new StringBuilder();
		sb.append("This is content of stuff " + stuff.getId() + ":" + System.getProperty("line.separator"));
		sb.append("id:" + stuff.getId() + System.getProperty("line.separator"));
		sb.append("description:" + stuff.getDescription() + System.getProperty("line.separator"));
		sb.append("details: " + stuff.getDetails());
		return sb.toString();
	}

	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}

}
