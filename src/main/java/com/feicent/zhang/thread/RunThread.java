package com.feicent.zhang.thread;

public class RunThread {
	private static int count = 0;
    private static boolean loop = true;
    
    public void startService() throws InterruptedException {
        loop =true;
        final RunThread test = new RunThread();
        if(!loop){
	        new Thread(new Runnable(){
	            public void run() {
	                try {
	                    test.stopService();
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
	             
	        }).start();
        }
        if(loop){
            test.handle();
        }
    }   
    @SuppressWarnings("static-access")
	public void stopService() throws InterruptedException{
            this.loop=false;
            System.out.println("开始停止线程...");
    }
    
    public void handle() throws InterruptedException {
           while(loop) {
        	  count++;
              System.out.println(count+". 当前时间: "+System.currentTimeMillis());
              Thread.sleep(1000);
           }          
    }
	public static void main(String[] args) throws InterruptedException {
		RunThread test = new RunThread();
		test.startService();
	}

}
