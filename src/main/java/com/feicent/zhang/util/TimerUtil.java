package com.feicent.zhang.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 计时器的几种实现方式
 * @author yzuzhang
 * 关闭计时器 timer.cancel();
 */
public class TimerUtil {
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
    public static void main(String[] args) {
    	//线程安全的DateFormat工具类
    	String d = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
    	System.out.println(d);
        //timer1();
        //timer2();
        //timer3();
        //timer4();
    	timer5();
    }

   /**
    * 第一种方法：设定指定任务task在指定时间time执行 
    * schedule(TimerTask task, long delay)
    */
    public static void timer1() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
            	execute();
            }
        }, 2000);// 设定指定的时间time,此处为2000毫秒
   }

   /**
    * 第二种方法：设定指定任务task在指定延迟delay后进行固定延迟peroid的执行
    * schedule(TimerTask task, long delay, long period)
    */
    public static void timer2() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
            	execute();
            }
        }, 1000, 5000);
    }

   /**
    * 第三种方法：设定指定任务task在指定延迟delay后进行固定频率peroid的执行。
    * scheduleAtFixedRate(TimerTask task, long delay, long period)
    */
    public static void timer3() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
            	execute();
            }
        }, 1000, 2000);
    }
   
    /**
     * 第四种方法：安排指定的任务task在指定的时间firstTime开始进行重复的固定速率period执行．
     * Timer.scheduleAtFixedRate(TimerTask task,Date firstTime,long period)
     */
    public static void timer4() {
       Calendar calendar = Calendar.getInstance();
       calendar.set(Calendar.HOUR_OF_DAY, 12); // 控制时
       calendar.set(Calendar.MINUTE, 0);       // 控制分
       calendar.set(Calendar.SECOND, 0);       // 控制秒
       
       Date time = calendar.getTime();         // 得出执行任务的时间,此处为今天的12：00：00
       
       Timer timer = new Timer();
       timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
            	execute();
            }
        }, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
    }
    
    /**
     * 强大的ScheduledExecutorService
     */
    public static void timer5() {
    	try {
            //定时任务
            ScheduledExecutorService service = Executors.newScheduledThreadPool(1); 
			Runnable runnable = new Runnable() { 		
			    public void run() {
			    	execute();
			    }  
			};
			//scheduleWithFixedDelay
			service.scheduleAtFixedRate(
					runnable, 
					0, //延迟时间
					5, //心跳间隔
					TimeUnit.SECONDS //间隔单位
			);
        } catch (Exception e) {
        	System.out.println("timer5:"+e.getMessage());
        }
    }
    
    private static void execute(){
    	System.out.println("Timer execute: "+sdf.format(new Date()));
    }
}
