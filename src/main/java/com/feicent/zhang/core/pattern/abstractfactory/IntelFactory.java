package com.feicent.zhang.core.pattern.abstractfactory;

import com.feicent.zhang.core.pattern.abstractfactory.entity.IntelCpu;
import com.feicent.zhang.core.pattern.abstractfactory.entity.IntelMainboard;
import com.feicent.zhang.core.pattern.abstractfactory.interfaces.Cpu;
import com.feicent.zhang.core.pattern.abstractfactory.interfaces.Mainboard;

public class IntelFactory implements AbstractFactory {
	
	private int nums = 0;
	
    public IntelFactory() {
		super();
	}
    
    public IntelFactory(int nums) {
		super();
		this.nums = nums;
	}
    
	@Override
    public Cpu createCpu() {
        return new IntelCpu(nums);
    }
    
    @Override
    public Mainboard createMainboard() {
        return new IntelMainboard(nums);
    }
    
}