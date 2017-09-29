package com.feicent.zhang.core.pattern.abstractfactory.entity;

import com.feicent.zhang.core.pattern.abstractfactory.interfaces.Cpu;

public class IntelCpu implements Cpu {
	
	/**
     * CPU的针脚数
     */
    private int pins = 0;
    
    public  IntelCpu(int pins){
        this.pins = pins;
    }
    
    @Override
    public void calculate() {
        System.out.println("Intel CPU的针脚数：" + pins);
    }
}
