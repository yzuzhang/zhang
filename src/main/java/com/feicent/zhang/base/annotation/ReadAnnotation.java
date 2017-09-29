package com.feicent.zhang.base.annotation;

import java.lang.reflect.Field;

/**
 * 注解的解析:说明如何解析注解
 * @author yzuzhang
 */
public class ReadAnnotation {
	
	public static void main(String[] args){
        // 读取类的注释
        BaseLog obj = new BaseLog();
        // Annotation[] arr = obj.getClass().getAnnotations(); //得到所有注释
        MyTable table = obj.getClass().getAnnotation(MyTable.class); // 取得指定注释
        
        System.out.println("类注释（name）: " + table.name());
        System.out.println("类注释（version）: " + table.version());

        // 读取属性的注释
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields)
        {
            // Annotation[] arr2 = f.getAnnotations(); //得到所有注释
            MyField ff = f.getAnnotation(MyField.class);// 取得指定注释
            if(ff != null)
            {
                System.out.println("属性（" + f.getName() + "）: " + ff.name() + " -- " + ff.type());
            }
        }
    }
}
