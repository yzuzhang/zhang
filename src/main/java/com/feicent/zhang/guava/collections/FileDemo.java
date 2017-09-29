package com.feicent.zhang.guava.collections;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.Test;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multiset;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.google.common.io.Resources;

/**
 * 
 * @author yzuzhang
 * @date 2016-06-23 
 */
public class FileDemo {

	@SuppressWarnings({ "unused", "deprecation" })
	@Test
	public void fileTest() throws IOException{
		File file = new File("");
		ImmutableList<String> lines = Files.asCharSource(file, Charsets.UTF_8).readLines();
		
		
		Multiset<String> wordOccurrences = HashMultiset.create(
				Splitter.on(CharMatcher.WHITESPACE)
				.trimResults()
				.omitEmptyStrings()
				.split(Files.asCharSource(file, Charsets.UTF_8).read()));
		
		HashCode hash = Files.asByteSource(file).hash(Hashing.sha1());
		URL url = new URL("");
		Resources.asByteSource(url).copyTo(Files.asByteSink(file));
	}
}
