package com.feicent.zhang.plugin.mybatis;

import java.util.List;
import java.util.Map;

public interface ArticleMapper {
	
	public int insertArticle(Map<String, Object> map);
	
	public List<Map<String, Object>> selectArticle(Map<String, Object> map);
}
