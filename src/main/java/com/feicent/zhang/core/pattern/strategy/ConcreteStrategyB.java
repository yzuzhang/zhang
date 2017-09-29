package com.feicent.zhang.core.pattern.strategy;

public class ConcreteStrategyB implements PriceStrategy {
	
	@Override
    public double calcPrice(double booksPrice) {
        System.out.println("对于中级会员的折扣为10%");
        return booksPrice * 0.9;
    }

}
