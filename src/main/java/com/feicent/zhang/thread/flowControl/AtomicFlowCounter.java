package com.feicent.zhang.thread.flowControl;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicFlowCounter {
	private final int maxflow;
    private AtomicInteger currentflow;
    private final String name;
 
    public AtomicFlowCounter(String name) {
        this(-1, name);
    }
 
    public AtomicFlowCounter(int maxflow, String name) {
        this.maxflow = maxflow;
        currentflow = new AtomicInteger(0);
        this.name = name;
    }
 
    public int getMaxflow() {
        return maxflow;
    }
 
    public int getCurrentflow() {
        return currentflow.get();
    }
 
    public void setCurrentflow(int currentflow) {
        this.currentflow = new AtomicInteger(currentflow);
    }
 
    public String getName() {
        return name;
    }
 
    public boolean incCounter() {
        for (;;) {
            int oldvalue = currentflow.get();
            int current = oldvalue + 1;
            if (current > maxflow) {
                return false;
            }
            if (currentflow.compareAndSet(oldvalue, current)) {
                System.out.println(Thread.currentThread() + "加流量"+current);
                return true;
            }
        }
 
//       if (currentflow.getAndIncrement() < maxflow) {
//       System.out.println(Thread.currentThread() + "加流量");
//       return true;
//       } else {
//       currentflow.decrementAndGet();
//       }
//       return false;
    }
 
    public boolean decCounter() {
        for (;;) {
            int oldvalue = currentflow.get();
            int current = oldvalue - 1;
            if (current < 0) {
                return false;
            }
            if (currentflow.compareAndSet(oldvalue, current)) {
                System.out.println(Thread.currentThread() + "减流量"+current);
                return true;
            }
        }
        // if (currentflow.getAndDecrement() > 0) {
        // System.out.println(Thread.currentThread() + "减流量");
        // return true;
        // } else {
        // currentflow.incrementAndGet();
        // }
        // return false;
    }
}
