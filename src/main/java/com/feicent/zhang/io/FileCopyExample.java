package com.feicent.zhang.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class FileCopyExample {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		
		System.out.print(FileUtils.readFileToString(new File("d:/Users/Administrator/Desktop/sdk.jmx")));
		//fileCopy();
	}

	@SuppressWarnings("deprecation")
	public static void fileCopy() {
		try {
			File src = new File("D:/temp/test.dat");
			File dest = new File("D:/temp/test.dat.bak");

			FileUtils.copyFile(src, dest);
		} catch (IOException ioe) {
			System.err.println("Problem copying file.");
		}

		try {
			File src = new File("D:/temp/test.dat");
			File dir = new File("D:/temp/directory");

			FileUtils.copyFileToDirectory(src, dir);
		} catch (IOException ioe) {
			System.err.println("Problem copying file to dir.");
		}

		try {
			String string = UUID.fromString("Blah blah blah").toString();
			File dest = new File("D:/temp/UUID.txt");
				
			FileUtils.writeStringToFile(dest, string, "ISO-8859-1");
		} catch (IOException ioe) {
			System.err.println("Error writing out a String.");
		}

		Writer writer = null;
		InputStream inputStream = null;
		try {
			writer = new FileWriter("test.dat");
			inputStream = FileCopyExample.class.getClass()
					.getResourceAsStream("/io/web.xml");
			IOUtils.copy(inputStream, writer);
		} catch (IOException e) {
			System.out.println("Error copying data");
		}finally{
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(writer);
		}

		try {
			File src = new File("D:/temp/test.txt");
			OutputStream output = new FileOutputStream(src);
			inputStream = FileCopyExample.class.getClass()
					.getResourceAsStream("/io/web.xml");
			IOUtils.copy(inputStream, output);
			// writer.close();
			inputStream.close();
			output.close();
		} catch (IOException e) {
			System.err.println("Error copying data");
		}

	}
	
	/**
	 * Deletes a file. If file is a directory, delete it and all sub-directories
	 */
	public static void deleteFile(){
		File file = new File(("io/project.properties"));
		String display = FileUtils.byteCountToDisplaySize(file.length());
		System.out.println("project.xml is " + display);
		try {
			FileUtils.forceDelete(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
