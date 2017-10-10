package com.feicent.zhang.util.number;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 保留两位小数4种方法
 * @author yzuzhang
 * @date 2017年10月10日 下午1:54:03
 */
public class DecimalFormatUtil {
	private static double price = 111231.5585;
	
    public static void m1() {
        BigDecimal bg = new BigDecimal(price);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(f1);
    }
    
    /**
     * DecimalFormat转换最简便
     */
    public static void m2() {
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println(df.format(price));
    }
    
    /**
     * String.format打印最简便
     */
    public static void m3() {
        System.out.println(String.format("%.2f", price));
    }
    
    public static void m4() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        System.out.println(nf.format(price));
    }
    
    public static void main(String[] args) {
    	DecimalFormatUtil.m1();
    	DecimalFormatUtil.m2();
    	DecimalFormatUtil.m3();
    	DecimalFormatUtil.m4();
    }
    
}
