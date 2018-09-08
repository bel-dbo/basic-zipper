package batch;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

public class ZipUtils {
	protected static final Logger log = Logger.getLogger(ZipUtils.class);

	private static final int BUFFER_SIZE = 4096;

	private static void extractFile(ZipInputStream in, File outdir, String name) throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(outdir, name)));
		int count = -1;
		while ((count = in.read(buffer)) != -1)
			out.write(buffer, 0, count);
		out.close();
	}

	private static void mkdirs(File outdir, String path) {
		File d = new File(outdir, path);
		if (!d.exists())
			d.mkdirs();
	}

	private static String dirpart(String name) {
		int s = name.lastIndexOf(File.separatorChar);
		return s == -1 ? null : name.substring(0, s);
	}

	/***
	 * Extract zipfile to outdir with complete directory structure
	 * 
	 * @param zipfile
	 *            Input .zip file
	 * @param outdir
	 *            Output directory
	 * @throws IOException
	 */
	public static void extract(File zipfile, File outdir) throws IOException {
		ZipInputStream zin = new ZipInputStream(new FileInputStream(zipfile));
		ZipEntry entry;
		String name, dir;
		while ((entry = zin.getNextEntry()) != null) {
			name = entry.getName();
			if (entry.isDirectory()) {
				mkdirs(outdir, name);
				continue;
			}
			/*
			 * this part is necessary because file entry can come before directory entry
			 * where is file located i.e.: /foo/foo.txt /foo/
			 */
			dir = dirpart(name);
			if (dir != null)
				mkdirs(outdir, dir);

			extractFile(zin, outdir, name);
		}
		zin.close();
	}

	private static List<String> generateFileList(String baseFolder, List<String> fileList, File node) {
		// add file only
		if (node.isFile()) {
			String nodeName = node.toString();
			fileList.add(nodeName.substring(baseFolder.length() + 1, nodeName.length()));
		}

		if (node.isDirectory()) {
			String[] subNote = node.list();
			for (String filename : subNote) {
				generateFileList(baseFolder, fileList, new File(node, filename));
			}
		}
		return fileList;
	}

	public static void compress(String source, String zipFile) throws Exception {
		byte[] buffer = new byte[1024];
		File baseFolder = new File(source);

		List<String> fileList = generateFileList(source, new ArrayList<String>(), baseFolder);
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		try {
			fos = new FileOutputStream(zipFile);
			zos = new ZipOutputStream(fos);

			log.debug("Compressing " + source + " to " + zipFile);
			FileInputStream in = null;

			for (String file : fileList) {
				log.debug("File Added : " + file);
				ZipEntry ze = new ZipEntry(source + File.separator + file);
				zos.putNextEntry(ze);
				try {
					in = new FileInputStream(source + File.separator + file);
					int len;
					while ((len = in.read(buffer)) > 0) {
						zos.write(buffer, 0, len);
					}
				} finally {
					in.close();
				}
			}

			zos.closeEntry();
			log.debug("Folder successfully compressed");

		} finally {
			try {
				zos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}