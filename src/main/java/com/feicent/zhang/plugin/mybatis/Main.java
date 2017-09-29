package com.feicent.zhang.plugin.mybatis;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.alibaba.fastjson.JSON;

public class Main {
	
	protected static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	protected static SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
	
	private static SqlSession sqlSession = null;
	private static SqlSessionFactory sqlSessionFactory = null;
	
	private static AuthorMaper authorMaper = null;
	protected static ArticleMapper articleMapper = null;
	
	public static void main(String[] args) {
		sqlSessionFactory = MyBatisUtils.getSqlSessionFactory();
		sqlSession = sqlSessionFactory.openSession();
		authorMaper = sqlSession.getMapper(AuthorMaper.class);
		articleMapper = sqlSession.getMapper(ArticleMapper.class);
		
		Map<String, Object> authorMap = new HashMap<String, Object>();
		authorMap.put("id", 3);
		authorMap.put("title", "我是标题");
		authorMap.put("description", "你好啊，今日头条！");
		int result =  authorMaper.insertAuthor(authorMap);
		System.out.println("保存结果: "+result);
		Map<String, Object> authr = authorMaper.selectAuthor(authorMap);
		System.out.println(JSON.toJSONString(authr));
	}

}
