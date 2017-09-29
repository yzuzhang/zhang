package com.feicent.zhang.thread.flowControl;

import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FlowControlInputStream extends FilterInputStream{
	
	@SuppressWarnings("unused")
	public static void main(String[] args){   
        try{   
            byte[] buffer = new byte[8092];          
            int n = -1;
            long start = System.currentTimeMillis();   
            FileInputStream in = new FileInputStream("E:\\APP_addShopingCart_5th10000.jtl");   
            //限制流量为10000也就是10000=10kbps
            FlowControlInputStream fin = new FlowControlInputStream(in, 40000);
           // fin.update(1000);            
            System.out.println("当前速率："+fin.check()/1000+"kbps");   
            System.out.println("当前文件大小:"+fin.available()/1024+"kb");
            while((n = fin.read(buffer)) > 0){
            	System.out.println(new String(buffer).length());
            }
            fin.close();
            long end = System.currentTimeMillis();
            System.out.println("读取文件完成，共花掉"+(end-start)/600+"秒");
        }catch(IOException e){   
            e.printStackTrace();   
        }   
    }  
	
	private long timestamp;
    private int maxbps;
    private int currentbps;
    private int bytesread;
      
    //----------------------------------------------------------   
    //constructor   
    public FlowControlInputStream(InputStream in, int maxbps){   
        super(in);   
        this.maxbps = maxbps;
        this.currentbps = 0;
        this.bytesread = 0;
        this.timestamp = System.currentTimeMillis();   
    }   
      
    //----------------------------------------------------------   
    //decorated methods   
      
    public int read() throws IOException{   
        synchronized(in){   
            int avaliable = check();   
            if(avaliable == 0){   
                waitForAvailable();   
                avaliable = check();
            }   
            int value = in.read();   
            update(1);   
            return value;   
        }   
    }   
  
    public int read(byte[] b) throws IOException{   
        return read(b, 0, b.length);   
         
    }   
  
    public int read(byte[] b, int off, int len) throws IOException{   
        synchronized(in){   
            int avaliable = check();
            if(avaliable == 0){
                waitForAvailable();
                avaliable = check();
            }          
            int n = in.read(b, off, Math.min(len, avaliable));   
            update(n);   
            return n;     
        }   
    }   
  
    private int check(){   
        long now = System.currentTimeMillis();   
        if(now - timestamp >= 1000){
            timestamp = now;   
            currentbps = bytesread;
            bytesread = 0;
            return maxbps;
        }else{   
            return maxbps - bytesread;   
        }   
    }   
  
    private void waitForAvailable(){   
        long time = System.currentTimeMillis() - timestamp;   
        boolean isInterrupted = false;   
        while(time < 1000){   
            try{   
                Thread.sleep(1000 - time);
            }catch(InterruptedException e){   
                isInterrupted = true;   
            }   
            time = System.currentTimeMillis() - timestamp;   
        }   
        if(isInterrupted)   
            Thread.currentThread().interrupt();   
        return;   
    }   
  
    private void update(int n){   
        bytesread += n;   
    }   
      
    public int getCurrentbps(){   
        return currentbps;   
    }

}
