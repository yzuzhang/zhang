package com.feicent.zhang.util.tool;

/**
 * 根据两点经纬度，计算距离、方位角
 * https://my.oschina.net/wjggwm/blog/910209
 * @date 2017年9月14日
 * 
 * 1.Lat1 Lung1 表示A点经纬度，Lat2 Lung2 表示B点经纬度
 * 2.a=Lat1–Lat2 为两点纬度之差, b=Lung1-Lung2 为两点经度之差
 * 3.6378.137为地球半径，单位为千米
 */
public class CalculateLengthByGps {
	
	//地球半径(m)
	private static final double EARTH_RADIUS = 6378137.0d;  

	/**
	 * 计算两点距离
	 * @param lat_a
	 * @param lng_a
	 * @param lat_b
	 * @param lng_b
	 * @return
	 */
	public static double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
	       double radLat1 = (lat_a * Math.PI / 180.0);

	       double radLat2 = (lat_b * Math.PI / 180.0);

	       double a = radLat1 - radLat2;

	       double b = (lng_a - lng_b) * Math.PI / 180.0;

	       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)

	              + Math.cos(radLat1) * Math.cos(radLat2)

	              * Math.pow(Math.sin(b / 2), 2)));

	       s = s * EARTH_RADIUS;

	       s = Math.round(s * 10000) / 10000;

	       return s;

	}
	
	/**
	 * 计算方位角pab
	 * @param lat_a  A的纬度
	 * @param lng_a  A的经度
	 * @param lat_b  B的纬度
	 * @param lng_b  B的经度
	 * @return
	 */
	public static double gps2d(double lat_a, double lng_a, double lat_b, double lng_b) {
		
        double d = 0;

        lat_a=lat_a*Math.PI/180;

        lng_a=lng_a*Math.PI/180;

        lat_b=lat_b*Math.PI/180;

        lng_b=lng_b*Math.PI/180;

        d= Math.sin(lat_a)*Math.sin(lat_b)+Math.cos(lat_a)*Math.cos(lat_b)*Math.cos(lng_b-lng_a);

        d = Math.sqrt(1-d*d);

        d = Math.cos(lat_b)*Math.sin(lng_b-lng_a)/d;

        d = Math.asin(d)*180/Math.PI;
       
        //d = Math.round(d*10000);
        return d;
	}
	
}
