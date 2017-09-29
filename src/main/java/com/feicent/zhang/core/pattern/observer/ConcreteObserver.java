package com.feicent.zhang.core.pattern.observer;

/**
 * 具体观察者角色类
 */
public class ConcreteObserver implements Observer {
	private String name;
    //观察者的状态
    private String observerState;
    
    public ConcreteObserver() {
		super();
	}
    
    public ConcreteObserver(String name) {
		super();
		this.name = name;
	}
    
    @Override
    public void update(String state) {
        /**
         * 更新观察者的状态，使其与目标的状态保持一致
         */
        observerState = state;
        System.out.println(name +"状态为："+ observerState);
    }

}
