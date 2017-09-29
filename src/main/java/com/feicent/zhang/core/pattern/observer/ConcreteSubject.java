package com.feicent.zhang.core.pattern.observer;

/**
 * 具体主题角色类
 * @author yzuzhang
 * @date 2017年8月2日
 */
public class ConcreteSubject extends Subject{
    
    private String state;
    
    public String getState() {
        return state;
    }
    
    public void change(String newState){
        state = newState;
        System.out.println("发布者状态为：" + state);
        //状态发生改变，通知各个观察者
        this.nodifyObservers(state);
    }
}
