package com.feicent.zhang.util.tool;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

/**
 * iteye: http://heipark.iteye.com/blog/1397513
 * IBM: http://www.ibm.com/developerworks/cn/java/j-lo-commonscli/
 * @date 2016年12月15日
 */
public class CommandParser {

	/**
	 * @param args
	 */
	public static void commandLineParser(String[] args) throws Exception {  
		  // Create a Parser  
		  CommandLineParser parser = new DefaultParser(); 
		  Options options = new Options( );  
		  options.addOption("h", "help", false, "Print this usage information");  
		  options.addOption("v", "verbose", false, "Print out VERBOSE information" );  
		  options.addOption("f", "file", true, "File to save program output to");  
		  // Parse the program arguments  
		  CommandLine commandLine = parser.parse( options, args );  
		  // Set the appropriate variables based on supplied options  
		  boolean verbose = false;  
		  String file = "";  
		   
		  if( commandLine.hasOption('h') ) {  
		    System.out.println( "Help Message");
		    System.exit(0);  
		  }  
		  if( commandLine.hasOption('v') ) {  
		    verbose = true;  
		  }  
		  if( commandLine.hasOption('f') ) {  
		    file = commandLine.getOptionValue('f');  
		  }  
		  System.out.println(file+", verbose="+verbose);
	}  
	
}
