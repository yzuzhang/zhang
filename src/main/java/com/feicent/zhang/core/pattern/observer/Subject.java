package com.feicent.zhang.core.pattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象主题角色类
 * 抽象主题角色把所有对观察者对象的引用保存在一个聚集（比如ArrayList对象）里，每个主题都可以有任何数量的观察者。
 * 抽象主题提供一个接口，可以增加和删除观察者对象，抽象主题角色又叫做抽象被观察者(Observable)角色
 * @date 2017年8月2日
 */
public class Subject {
	/**
     * 用来保存注册的观察者对象
     */
    private List<Observer> list = new ArrayList<Observer>();
    
    /**
     * 注册观察者对象
     * @param observer    观察者对象
     */
    public void attach(Observer observer){
    	list.add(observer);
        System.out.println("Attached an observer");
    }
    
    /**
     * 注册观察者对象
     * @param list    观察者对象列表
     */
    public void attach(List<Observer> list){
    	list.addAll(list);
    }
    
    /**
     * 删除观察者对象
     * @param observer    观察者对象
     */
    public void detach(Observer observer){
    	list.remove(observer);
    }
    
    /**
     * 通知所有注册的观察者对象
     */
    public void nodifyObservers(String newState){
    	for(Observer observer : list){
            observer.update(newState);
        }
    }
    
}
