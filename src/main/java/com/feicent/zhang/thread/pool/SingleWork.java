package com.feicent.zhang.thread.pool;

public class SingleWork extends MainWork{
	private static final long serialVersionUID = 1862565215099187356L;

    private String name;
    
    public SingleWork() {

    }
    
    public SingleWork(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object call() throws Exception {
        System.out.println("SingleWork, " + getName());
        return null;
    }
}
