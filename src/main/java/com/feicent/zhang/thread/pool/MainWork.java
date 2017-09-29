package com.feicent.zhang.thread.pool;

import java.io.Serializable;
import java.util.concurrent.Callable;

public class MainWork implements Serializable, Callable<Object> {
	private static final long serialVersionUID = -5913679670730902792L;

    public MainWork() {

    }

    public MainWork(String workname) {
        this.workname = workname;
    }

    private String workname;

    public String getWorkname() {
        return workname;
    }

    public void setWorkname(String workname) {
        this.workname = workname;
    }

    public Object call() throws Exception {
        System.out.println("MainWork, " + getWorkname());
        Thread.sleep(5000); // 睡眠5秒
        return null;
    }
}
