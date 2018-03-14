package com.feicent.zhang;

import org.apache.commons.io.FilenameUtils;

public class FileNameTest {

	public static void main(String[] args) {
		String filename = "D:/zhang/jing/ptest.jpg";
		System.out.println(FilenameUtils.getBaseName(filename)); // ptest
		System.out.println(FilenameUtils.getExtension(filename));// jpg
		System.out.println(FilenameUtils.getFullPath(filename)); // D:/zhang/jingdong/
		System.out.println(FilenameUtils.getFullPathNoEndSeparator(filename));// D:/zhang/jingdong
		System.out.println(FilenameUtils.getName(filename));// ptest.jpg
		System.out.println(FilenameUtils.getPath(filename));// zhang/jingdong/
		System.out.println(FilenameUtils.getPathNoEndSeparator(filename));// zhang/jingdong
		System.out.println(FilenameUtils.getPrefix(filename));// D:/
		System.out.println(FilenameUtils.getPrefixLength(filename));// 3
		
		String[] extensions = {"jpg", "png"};
		System.out.println(FilenameUtils.isExtension(filename, extensions)); // true
		
		System.out.println(FilenameUtils.separatorsToSystem(filename));
		System.out.println(FilenameUtils.separatorsToWindows(filename));
		
		System.out.println(FilenameUtils.removeExtension(filename)); // D:/zhang/jingdong/ptest
		System.out.println(FilenameUtils.normalize(filename));
	}

}
