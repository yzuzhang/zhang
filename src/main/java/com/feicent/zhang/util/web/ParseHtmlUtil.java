package com.feicent.zhang.util.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 从网页抓取图片工具类
 */
public class ParseHtmlUtil {
	
	/**
	 * @param url 网址
	 * @param encoding 编码
	 * @return 返回string
	 */
	public static String getHtmlResouceUrl(String url,String encoding){
		StringBuffer buffer= new StringBuffer();
		URL urlObj=null;
		URLConnection uc=null;
		InputStreamReader isr=null;
		BufferedReader reader=null;
		String line=null;
		try {
			urlObj = new URL(url);
			uc = urlObj.openConnection();
			isr = new InputStreamReader(uc.getInputStream(), encoding);
			reader = new BufferedReader(isr);
			while((line=reader.readLine())!=null){
				buffer.append(line+"\n");
			}
			
		}catch (MalformedInputException e) {
			e.printStackTrace();
			System.out.println("网络打开失败");
		}catch (IOException e) {
			e.printStackTrace();
			System.out.println("网络连接失败");
		}finally{
			IOUtils.closeQuietly(reader, isr);
		}
		return buffer.toString();
		
	}
	
	public static List<HashMap<String,String>> getHotleinfo(String url,String econding){
		String html=getHtmlResouceUrl(url,econding);
		Document document= Jsoup.parse(html);
		//获取最外层DIV host_list
		//Element element= document.getElementById("hot_list");
		//获取酒店结果列表
		Elements elements=document.getElementsByClass("searchresult_info");
		//创建list集合
		List<HashMap<String,String>> maps=new ArrayList<HashMap<String,String>>();
		for(Element el:elements){
			HashMap<String,String> map=new HashMap<String,String>();
			//获取酒店图片
			String imgsrc=el.getElementsByTag("img").attr("src");
			//获取酒店title
			String imgtitle=el.getElementsByTag("img").attr("alt");
			//获取酒店介绍
			String content=el.getElementsByClass("searchresult_htladdress").text();
			
			map.put("imgsrc", imgsrc);
			map.put("imgtitle", imgtitle);
			map.put("content", content);
			maps.add(map);
		}
		return maps;
	}
	
	public static void main(String[] args) throws IOException {
		String url="http://hotels.ctrip.com/";
		String econding="UTF-8";
		String html=getHtmlResouceUrl(url, econding);
		System.out.println(html);
		
		//解析源代码
		Document document= Jsoup.parse(html);
		document = Jsoup.connect(url)
				.data(new HashMap<String,String>())
				.cookie("jd", "ddd")
				.timeout(30000)
				.data("query", "Java")   // 请求参数
				.userAgent("I ’ m jsoup") // 设置 User-Agent 
				.cookie("auth", "token") // 设置 cookie 
				.timeout(3000)           // 设置连接超时时间
				.post();                 // 使用 POST 方法访问 URL 
		
		//document = Jsoup.connect(url).get();
		//获取最外层DIV host_list
		//Element element= document.getElementById("hot_list");
		//获取酒店结果列表
		Elements elements=document.getElementsByClass("searchresult_info");
		
		//创建list集合
		List<HashMap<String,String>> maps=new ArrayList<HashMap<String,String>>();
		for(Element el:elements){
			HashMap<String,String> map=new HashMap<String,String>();
			//获取酒店图片
			String imgsrc = el.getElementsByTag("img").attr("data-lazy");
			//获取酒店title
			String imgtitle = el.getElementsByTag("img").attr("alt");
			//获取酒店介绍
			String content = el.getElementsByClass("searchresult_htladdress").text();
			
			map.put("imgsrc", imgsrc);
			map.put("imgtitle", imgtitle);
			map.put("content", content);
			maps.add(map);
			if(maps!=null&&maps.size()>0){
				for(HashMap<String,String> map1:maps){
					System.out.println("酒店名称:"+map1.get("imgtitle"));
					System.out.println("酒店图片:"+map1.get("imgsrc"));
					System.out.println("酒店地址:"+map1.get("content"));
					System.out.println("-------------------end-----");
				}
			}
		}
	}

}