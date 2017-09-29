package com.feicent.zhang.thread.queue;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

public class Executor implements Runnable{
     
    private long createTime;
    private long timeout;
    
    private OutputStreamWriter writer;
    
    public Executor(long timeout, OutputStreamWriter writer){
        this.timeout = timeout;
        createTime = new Date().getTime();
        this.writer = writer;
    }
    
    @Override
    public void run() {
        try {
            writer.write("createTime>>[" + createTime + "]  timeout>>[" + timeout + "] hashcode>>[" + (createTime + timeout) + "]\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public long getDeadline(){
        return this.createTime + this.timeout;
    }
}