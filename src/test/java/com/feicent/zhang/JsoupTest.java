package com.feicent.zhang;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupTest {

    //图片保存路径
    private static final String saveImgPath = "D:/zhang/jsoup/";
    //定义想要爬取数据的地址
    private static final String url = "https://car.autohome.com.cn/zhaoche/pinpai/";
    
    public static void main(String[] args) throws IOException {
    	jsoupCarInfo();
	}

    /**
    * 品牌名称 和图片爬取和添加
    * https://www.cnblogs.com/fengzhifei/p/8383448.html
    */
    public static void jsoupCarInfo() throws IOException {
        //获取网页文本
        Document doc = Jsoup.connect(url).get();
        //根据类名获取文本内容
        Elements elementsByClass = doc.getElementsByClass("uibox-con");
        //遍历类的集合
        for (Element element : elementsByClass) {
            //获取类的子标签数量
            int childNodeSize_1 = element.childNodeSize();
            //循环获取子标签内的内容
            for (int i = 0; i < childNodeSize_1; i++) {
                //获取车标图片地址
                String tupian = element.child(i).child(0).child(0).child(0).child(0).attr("src");
                //获取品牌名称
                String pinpai = element.child(i).child(0).child(1).text();
                //输出获取内容看是否正确
                System.out.println("车标图片地址-----------" + tupian);
                System.out.println("品牌-----------" + pinpai);
                System.out.println();
                //把车标图片保存到本地
                String tupian_1 = "http:"+tupian;
                //连接url
                URL url1 = new URL(tupian_1);
                URLConnection uri=url1.openConnection();
                
                //获取数据流
                InputStream in = uri.getInputStream();
                //获取后缀名
                String imageName = tupian.substring(tupian.lastIndexOf("/") + 1,tupian.length());
                //写入数据流
                OutputStream os = new FileOutputStream(new File(saveImgPath, imageName));
                //IOUtils.copy(in, os);
                byte[] buf = new byte[1024];
                int p = 0;
                while((p=in.read(buf)) != -1){
                    os.write(buf, 0, p);
                }
                os.close();
                
                /**
                 * 因为每个品牌下有多个合资工厂
                 * 比如一汽大众和上海大众还有进口大众
                 * 所有需要循环获取合资工厂名称和旗下车系
                 */
                
                /**
                 * 获取标签下子标签数量
                 * 如果等于1则没有其他合资工厂
                 */
                int childNodeSize_3 = element.child(i).child(1).childNodeSize();
                if(childNodeSize_3 == 1){
                	//获取车系数量
                    int childNodeSize_2 = element.child(i).child(1).child(0).childNodeSize();
                    //循环获取车系信息
                    for (int j = 0; j < childNodeSize_2; j++) {
                        String chexi = element.child(i).child(1).child(0).child(j).child(0).child(0).text();
                        System.out.println("车系-----------" + chexi);
                    }
                } else {
                    /**
                     * 如果childNodeSize_3大于1
                     * 则有多个合资工厂
                     */
                    //分别获取各个合资工厂旗下车系
                    for (int j = 0; j < childNodeSize_3; j++) {
                        /**
                         * 如果j是单数则是合资工厂名称
                         * 否则是车系信息
                         */
                        int k = j%2;
                        if(k == 0){
                            //获取合资工厂信息
                            String hezipinpai = element.child(i).child(1).child(j).child(0).text();
                            System.out.println("合资企业名称-----------" + hezipinpai);
                        } else {
                        	int childNodeSize_4 = element.child(i).child(1).child(j).childNodeSize();
                            //循环获取合资工厂车系信息
                            for(int l = 0; l < childNodeSize_4; l++){
                                String chexi = element.child(i).child(1).child(j).child(l).child(0).child(0).text();
                                System.out.println("车系-----------" + chexi);
                            }
                        }
                    }
                    
                }
                
                System.out.println("************************");
                System.out.println("************************");
            }
        }
    }

}
