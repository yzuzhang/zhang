package com.feicent.zhang.thread.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class MainTest {

	public static void main(String[] args) {
        MainWork mainWork  = new MainWork("Red");
        MainWork mainWork1 = new MainWork("Blue");
        MainWork mainWork2 = new MainWork("Gray");
        MainWork mainWork3 = new MainWork("张三");
        MainWork mainWork4 = new MainWork("李四");
        
        SingleWork SingleWork  = new SingleWork("Red");
        SingleWork SingleWork1 = new SingleWork("Blue");
        SingleWork SingleWork2 = new SingleWork("Gray");
        SingleWork SingleWork3 = new SingleWork("张三");
        SingleWork SingleWork4 = new SingleWork("李四");

        List<MainWork> list = new ArrayList<MainWork>();
        list.add(mainWork);
        list.add(mainWork);
        list.add(mainWork);
        list.add(mainWork);
        list.add(mainWork);
        list.add(mainWork);
        
        list.add(SingleWork);
        list.add(SingleWork1);
        list.add(mainWork1);
        list.add(SingleWork2);
        list.add(mainWork2);
        
        list.add(SingleWork3);
        list.add(mainWork3);
        list.add(mainWork4);
        list.add(SingleWork4);
        
        ExecutorService threadpool = null;
        try {
        	//线程池大小为5,排队执行
        	threadpool = Threadpool.getInstance();
            @SuppressWarnings("unused")
			List<Future<Object>> lists = threadpool.invokeAll(list);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        	if (threadpool != null) {
        		System.out.println("执行完毕, 关闭线程池...");
        		threadpool.shutdown();
			}
        }
	}
}
