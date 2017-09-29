package com.feicent.zhang.thread.notify;

public class ThreadB extends Thread{
	private Object lock;

	public ThreadB(Object lock) {
		super();
		this.lock = lock;
	}
	
	@Override
	public void run() {
		try {
			synchronized(lock){
				for (int i = 1; i <= 10; i++) {
					MyList.add();
					System.out.println("List添加了"+ i +"个元素");
					if ( MyList.size() == 5 ) {
						//唤醒调用了wait()操作而处于阻塞状态的线程
						lock.notify();
						System.out.println("ThreadB Notify已经发出");
					}
					Thread.sleep(1000);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
