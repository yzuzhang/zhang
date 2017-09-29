package com.feicent.zhang.core.pattern.observer;

public class Main {
	
	public static void main(String[] args) {
        //创建主题对象
        ConcreteSubject subject = new ConcreteSubject();
        //创建观察者对象
        Observer observerA = new ConcreteObserver("观察者A");
        Observer observerB = new ConcreteObserver("观察者B");
        //将观察者对象登记到主题对象上
        subject.attach(observerA);
        subject.attach(observerB);
        //改变主题对象的状态
        subject.change("上线");
    }
	
}
