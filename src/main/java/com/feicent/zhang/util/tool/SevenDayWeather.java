package com.feicent.zhang.util.tool;

import java.net.HttpURLConnection;
import java.net.URL;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;

import com.feicent.zhang.io.FileUtil;

import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * 解析中国天气网页面获取七日天气
 * http://www.cnblogs.com/maxuewei2/p/7327379.html
 * @author yzuzhang
 * @date 2017年8月10日
 */
public class SevenDayWeather {
	
    // 从城市名称到城市代码的映射map，如 山东济宁->101120701
    private static HashMap<String, String> cityMap = new HashMap<String, String>();
    
    static {
    	initCityMap("cities.txt");
    }
    
    // 初始化cityMap
    private static void initCityMap(String fileName) {
        List<String> list = FileUtil.getListFromResource(fileName);
		for(String line : list) {
			String[] strs = line.split("\t");
			cityMap.put(strs[0], strs[1]);
		}
    }
    
    // 输入城市名，返回城市代码
    public static String cityToCode(String cityName) {
    	return cityMap.get(cityName);
    }
    
    // 输入城市代码，返回该城市的七日天气列表
    public static List<DayWeather> getSevenDayWeather(String cityCode) {
        String urlString = "http://www.weather.com.cn/weather/" + cityCode + ".shtml";
        List<DayWeather> weatherList = new ArrayList<DayWeather>();
        try {
			Parser parser = new Parser((HttpURLConnection) (new URL(urlString)).openConnection());
			HasAttributeFilter t_clearfix = new HasAttributeFilter("class", "t clearfix");
			NodeIterator iterator = parser.extractAllNodesThatMatch(t_clearfix).elements();
			Node ul = iterator.nextNode();
			NodeList lis = ul.getChildren();
			TagNameFilter li = new TagNameFilter("li");
			NodeList liList = lis.extractAllNodesThatMatch(li);
			
			for (int i = 0; i < liList.size(); i++) {
			    NodeList children = liList.elementAt(i).getChildren();
			    TagNameFilter p = new TagNameFilter("p");
			    NodeList ps = children.extractAllNodesThatMatch(p);
			    TagNameFilter h1 = new TagNameFilter("h1");
			    NodeList h = children.extractAllNodesThatMatch(h1);
			    String day = h.elementAt(0).toPlainTextString().trim();
			    String weather = ps.elementAt(0).toPlainTextString().trim();
			    String temperature = ps.elementAt(1).toPlainTextString().trim();
			    String wind = ps.elementAt(2).toPlainTextString().trim();
			    DayWeather dw = new DayWeather(day, weather, temperature, wind);
			    weatherList.add(dw);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("发生网络错误或解析错误");
		}
        
        return weatherList;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入你的城市，例如'广东深圳' ：");
        String cityName = sc.nextLine();
        sc.close();
        
        String cityCode = cityToCode(cityName);
        if (cityCode == null) {
            System.out.println("该城市不存在: "+cityName);
            return;
        }
        System.out.println("该城市代码为 " + cityCode);
        
        List<DayWeather> sw = getSevenDayWeather(cityCode);
        System.out.println("=============== 七日天气 =====================");
        System.out.println("日期" + "\t\t" + "天气" + "\t" + "温度" + "\t" + "风");
        System.out.println("-----------------------------------------");
        for (int i = 0; i < sw.size(); i++) {
            System.out.println(sw.get(i));
        }
    }
}

//定义“一日天气”类
class DayWeather {
	String day;// 日期
	String weather;// 天气
	String temperature;// 温度
	String wind;// 风

	DayWeather(String day, String weather, String temperature, String wind) {
		this.day = day;
		this.weather = weather;
		this.temperature = temperature;
		this.wind = wind;
	}

	@Override
	public String toString() {
		return new String(day + "\t" + weather + "\t" + temperature + "\t" + wind);
	}
}
