package com.feicent.zhang.core.pattern.strategy;

public class ConcreteStrategyC implements PriceStrategy {
	
	@Override
	public double calcPrice(double booksPrice) {
		 System.out.println("对于高级会员的折扣为20%");
	     return booksPrice * 0.8;
	}

}
