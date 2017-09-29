package com.feicent.zhang.project.renren.util;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Objects;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 降级特技之配置中心
 * http://zhuanlan.51cto.com/art/201704/537274.htm
 */
public class JiangjiService {

	private static ClassPathResource resource;
	private static WatchService watchService;
	public static  Properties properties;
	private static String filename = "application.properties";

	static { 
        try {
            resource= new ClassPathResource(filename); 
            //监听filename所在目录下的文件修改、删除事件 
            watchService = FileSystems.getDefault().newWatchService(); 
            Paths.get(resource.getFile().getParent()).register(watchService, 
            		StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE); 
            properties= PropertiesLoaderUtils.loadProperties(resource); 
        } catch(IOException e) {
        	e.printStackTrace();
        } 
        
        //启动一个线程监听内容变化，并重新载入配置 
        Thread watchThread = new Thread(() -> { 
            while(true) { 
                try{ 
                   WatchKey watchKey = watchService.take(); 
                   for (WatchEvent<?> event : watchKey.pollEvents()) { 
                       if (Objects.equals(event.context().toString(), filename)){ 
                            properties =PropertiesLoaderUtils.loadProperties (resource); 
                            break; 
                       } 
                   } 
                   watchKey.reset(); 
                } catch (Exception e){
                	e.printStackTrace();
                } 
            } 
        }); 
       watchThread.setDaemon(true); 
       watchThread.start(); 
      
        Runtime.getRuntime().addShutdownHook(new Thread(() -> { 
            try{ 
                watchService.close(); 
            } catch(IOException e) {e.printStackTrace();} 
        })); 
    } 
}
