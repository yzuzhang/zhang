package com.feicent.zhang;

import java.util.ArrayList;
import java.util.List;

import com.feicent.zhang.entity.User;

/**
 * 多线程 分批次处理数据
 * @author yzuzhang
 * @date 2017年9月28日 下午6:39:02
 */
public class MyHandler {
	
	private static final int THREAD_COUNT = 10; //开启线程数，根据实际需求调整
	private static final int HANDLE_COUNT = 500;//每次处理的个数,可以自己设置

	public static void main(String[] args) {
		List<User> list = new ArrayList<User>();//模拟list
		int totalCount = list.size();
		
		int countPerThread = totalCount/THREAD_COUNT;
		
		for (int i = 0; i < THREAD_COUNT; i++) {
			int startIndex = i * countPerThread;
			int endIndex = (i + 1) * countPerThread;
			if(i == (THREAD_COUNT - 1)){
				endIndex = totalCount;//最后一个线程的下标,设置为总数
			}
			
			List<User> subList = list.subList(startIndex, endIndex);
			Thread thread = new MyThread(subList, HANDLE_COUNT);
			thread.start();
		}
	}

}

class MyThread extends Thread {
	private List<User> list;
	private int count;//每次处理的个数,可以自己设置
	
	public MyThread(List<User> list, int count){
		this.list = list;
		this.count = count;
	}
	
	public void run() {
		int totalCount = list.size();
		for (int i = 0; i < totalCount; i = i + count) {
			int end = i + count;
			if(end > totalCount){
				end = totalCount; 
			}
			// 获取其中count个数据
			List<User> subList = list.subList(i, end);
			UserService.doSaveOrUpdate(subList);
		}
	}
	
}

class UserService {
	
	public static void doSaveOrUpdate(List<User> list){
		//从数据查询是否存在记录，返回userName数组
		List<String> userNameList = new ArrayList<String>();//此处模拟， 实际使用方式 List<String> userNameList= userDao.queryByList(tmpList);
	    //list = userDao.queryByList(tmpList);//
		//select userName from t_user where userName in (....);//userMapper.xml里实现
		
		List<User> saveList = new ArrayList<User>();
		List<User> updateList = new ArrayList<User>();
		
		for (User user : list) {
			if (userNameList.contains(user.getUserName())) {
				updateList.add(user);//数据库已经存在，则更新
			} else {
				saveList.add(user);
			}
		}
		
		if (saveList.size() > 0) {
			//userDao.saveByList(saveList);//新增数据
		}
		
		if (updateList.size() > 0) {
			//userDao.updateByList(updateList);//更新数据
		}
	}
	
}
