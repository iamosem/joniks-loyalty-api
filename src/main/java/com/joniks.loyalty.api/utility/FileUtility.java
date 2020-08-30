package com.joniks.lotalty.api.utility;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;

import com.joniks.lotalty.api.logger.DebugManager;

public class FileUtility {

	private static final DebugManager logger = DebugManager.getInstance(FileUtility.class);
	private static final int BUFFER_SIZE = 2048;

	public static void zipFiles(List<String> files, ServletOutputStream sos) throws IOException {
		ZipOutputStream zos = new ZipOutputStream(sos);
		byte bytes[] = new byte[BUFFER_SIZE];

		for (String fileName : files) {

			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);

			zos.putNextEntry(new ZipEntry(fileName.substring(fileName.lastIndexOf(File.separator) + 1)));

			int bytesRead;
			while ((bytesRead = bis.read(bytes)) != -1) {
				zos.write(bytes, 0, bytesRead);
			}
			zos.closeEntry();
			bis.close();
			fis.close();
		}
		zos.flush();
		zos.close();
	}

	/**
	 * Compresses a list of files to a destination zip file
	 * 
	 * @param listFiles   A collection of files and directories
	 * @param destZipFile The path of the destination zip file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void zipDirectories(List<String> listFiles, ServletOutputStream sos) {
		ZipOutputStream zos = new ZipOutputStream(sos);
		try {
			for (String fileLocation : listFiles) {
				File file = new File(fileLocation);
				if (file.isDirectory()) {
					zipDirectory(file, file.getName(), zos);
				} else {
					zipFile(file, zos);
				}
			}
			zos.flush();
			zos.close();
		} catch (Exception e) {
			logger.debug(StackTraceUtility.convertStackTraceToString(e));
		} finally {

		}
	}

	/**
	 * Adds a directory to the current zip output stream
	 * 
	 * @param folder       the directory to be added
	 * @param parentFolder the path of parent directory
	 * @param zos          the current zip output stream
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void zipDirectory(File folder, String parentFolder, ZipOutputStream zos) {
		for (File file : folder.listFiles()) {
			try {
				if (file.isDirectory()) {
					zipDirectory(file, parentFolder + "/" + file.getName(), zos);
					continue;
				}
				zos.putNextEntry(new ZipEntry(parentFolder + "/" + file.getName()));
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
				long bytesRead = 0;
				byte[] bytesIn = new byte[BUFFER_SIZE];
				int read = 0;
				while ((read = bis.read(bytesIn)) != -1) {
					zos.write(bytesIn, 0, read);
					bytesRead += read;
				}
				try {
					zos.closeEntry();
				} catch (Exception e) {
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (Exception e) {
						}
					}
					bis = null;
				}
			} catch (Exception e) {
				logger.debug(StackTraceUtility.convertStackTraceToString(e));
			}
		}
	}

	/**
	 * Adds a file to the current zip output stream
	 * 
	 * @param file the file to be added
	 * @param zos  the current zip output stream
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void zipFile(File file, ZipOutputStream zos) {

		BufferedInputStream bis = null;
		try {
			zos.putNextEntry(new ZipEntry(file.getName()));
			bis = new BufferedInputStream(new FileInputStream(file));
			long bytesRead = 0;
			byte[] bytesIn = new byte[BUFFER_SIZE];
			int read = 0;
			while ((read = bis.read(bytesIn)) != -1) {
				zos.write(bytesIn, 0, read);
				bytesRead += read;
			}
			zos.closeEntry();
		} catch (Exception e) {

		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (Exception e) {
				}
			}
			bis = null;
		}
	}
}
