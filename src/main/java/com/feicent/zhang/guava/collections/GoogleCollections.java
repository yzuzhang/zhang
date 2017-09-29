package com.feicent.zhang.guava.collections;

import java.util.List;
import java.util.Set;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Google collections
 * 这是所知道的最好的扩展实现包
 * 其中一些被社区叫嚣着要加入JDK
 */
public class GoogleCollections {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// create an ArrayList with three arguments
		List<String> list = Lists.newArrayList("foo", "bar", "baz");
		
		// notice that there is no generics or class cast,
		// and still this line does not generate a warning.
		Set<String> s = Sets.newConcurrentHashSet();

		// intersect and union are basic features of a Set, if you ask me
		//Set<String> s1 = Sets.intersect(s1, s2);

		// Example  of multiple values in a Map
		/*ListMultimap<String, Validator> validators = new ArrayListMultimap<String, Validator>();
		validators.put("save", new RequiredValidator());
		validators.put("save", new StringValidator());
		validators.put("delete", new NumberValidator());

		validators.get("save"); // { RequiredValidator, StringValidator }
		validators.get("foo");  // empty List (not null)
		validators.values();    // { RequiredValidator, StringValidator, NumberValidator }
*/	}

}
