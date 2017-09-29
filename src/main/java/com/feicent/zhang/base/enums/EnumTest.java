package com.feicent.zhang.base.enums;

public class EnumTest {
	
	public static void main(String[] args) {
		System.out.println(ColorEnum.GREEN.toString());
		System.out.println(ColorEnum.from(3).toString());
		
		System.out.println(ColorEnum.getName(1));
		System.out.println(ColorEnum.YELLO.getName());
		
		//枚举类比较 可以用 ==和 equals
		ColorEnum red = ColorEnum.RED;
		ColorEnum color = ColorEnum.from(1);
		System.out.println(red == color);
		System.out.println(red.equals(color));
	}
}
