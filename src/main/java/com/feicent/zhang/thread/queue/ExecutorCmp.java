package com.feicent.zhang.thread.queue;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

public class ExecutorCmp implements Runnable, Comparable<ExecutorCmp>{
     
    private long createTime;
     
    private long timeout;
     
    private OutputStreamWriter writer;
     
    public ExecutorCmp(long timeout, OutputStreamWriter writer){
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
     
    @Override
    public int compareTo(ExecutorCmp arg0) {
        return (int)((this.createTime + this.timeout) - (arg0.createTime + arg0.timeout));
    }
}
