package com.feicent.zhang.core.pattern.observer;

import java.util.Observable;

public class Watcher implements java.util.Observer {
    
    public Watcher(Observable o){
        o.addObserver(this);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("状态发生改变：" + ((Watched)o).getData());
    }

}
