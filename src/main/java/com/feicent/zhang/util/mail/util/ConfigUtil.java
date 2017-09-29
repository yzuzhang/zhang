package com.feicent.zhang.util.mail.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
    
	/**
     * 读取Properties配置文件
     */
    public static Properties getPropertiesFile(String confPath) throws IOException {
        InputStream is = ConfigUtil.class.getClassLoader().getResourceAsStream(confPath);
        Properties prop = new Properties();
        prop.load(is);
        is.close();
        return prop;
    }

}
