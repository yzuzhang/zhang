package com.feicent.zhang.core.pattern.abstractfactory;

/**
 * 抽象工厂模式
 * http://www.cnblogs.com/java-my-life/archive/2012/03/28/2418836.html
 */
public class Main {
	
	public static void main(String[]args){
        //创建装机工程师对象
        ComputerEngineer engineer = new ComputerEngineer();
        
        //客户选择并创建需要使用的产品对象
        AbstractFactory af = new IntelFactory(751);
        //告诉装机工程师自己选择的产品，让装机工程师组装电脑
        engineer.makeComputer(af);
        
        AbstractFactory df = new AmdFactory(938);
        engineer.makeComputer(df);
    }
}
