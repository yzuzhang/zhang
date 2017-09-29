package com.feicent.zhang.core.pattern.abstractfactory;

import com.feicent.zhang.core.pattern.abstractfactory.entity.IntelCpu;
import com.feicent.zhang.core.pattern.abstractfactory.entity.IntelMainboard;
import com.feicent.zhang.core.pattern.abstractfactory.interfaces.Cpu;
import com.feicent.zhang.core.pattern.abstractfactory.interfaces.Mainboard;

public class AmdFactory implements AbstractFactory {
	
    private int nums = 0;
	
    public AmdFactory() {
		super();
	}
    
    public AmdFactory(int nums) {
		super();
		this.setNums(nums);
	}
    
    @Override
    public Cpu createCpu() {
        return new IntelCpu(nums);
    }
    
    @Override
    public Mainboard createMainboard() {
        return new IntelMainboard(nums);
    }

	public int getNums() {
		return nums;
	}

	public void setNums(int nums) {
		this.nums = nums;
	}

}
