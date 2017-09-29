package com.feicent.zhang.core.pattern.strategy;

public class Main {
	
	public static void main(String[] args) {
        //选择并创建需要使用的策略对象
        PriceStrategy strategy = new ConcreteStrategyC();
        //创建环境
        Context context = new Context(strategy);
        //计算价格
        double price = 300;
        double quote = context.quote(price);
        System.out.println("图书原价："+price+"，打折后最终价格为：" + quote);
    }
}
