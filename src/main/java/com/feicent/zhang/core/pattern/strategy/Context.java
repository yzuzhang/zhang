package com.feicent.zhang.core.pattern.strategy;

/**
 * 环境(Context)角色：持有一个Strategy的引用
 * @date 2017年8月2日
 * http://www.cnblogs.com/java-my-life/archive/2012/05/10/2491891.html
 */
public class Context {
	
	//持有一个具体策略的对象
    private PriceStrategy strategy;
    
    /**
     * 构造函数，传入一个具体策略对象
     * @param strategy    具体策略对象
     */
    public Context(PriceStrategy strategy){
        this.strategy = strategy;
    }
    
    /**
     * 计算图书的价格
     * @param booksPrice    图书的原价
     * @return    计算出打折后的价格
     */
    public double quote(double booksPrice){
        return this.strategy.calcPrice(booksPrice);
    }
    
}
