package com.feicent.zhang.core.pattern.abstractfactory;

import com.feicent.zhang.core.pattern.abstractfactory.interfaces.Cpu;
import com.feicent.zhang.core.pattern.abstractfactory.interfaces.Mainboard;

/**
 * 抽象工厂接口
 * http://www.cnblogs.com/java-my-life/archive/2012/03/28/2418836.html
 */
public interface AbstractFactory {
	
	/**
     * 创建CPU对象
     * @return CPU对象
     */
    public Cpu createCpu();
    
    /**
     * 创建主板对象
     * @return 主板对象
     */
    public Mainboard createMainboard();
    
}
