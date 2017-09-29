package com.feicent.zhang.plugin.mybatis;

import java.util.Map;

public interface AuthorMaper {
	
	public int insertAuthor(Map<String, Object> map);
	
	public Map<String, Object> selectAuthor(Map<String, Object> map);
}
