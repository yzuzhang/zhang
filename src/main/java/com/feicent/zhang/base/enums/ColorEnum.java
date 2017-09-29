package com.feicent.zhang.base.enums;

/**
 * http://blog.csdn.net/qq_27093465/article/details/52180865
 * http://www.cnblogs.com/lizhen-home/p/7307309.html
 * 枚举类名 建议带上Enum后缀
 */
public enum ColorEnum {
	// 枚举成员名称全部大写
	RED("红色", 1), GREEN("绿色", 2), BLANK("白色", 3), YELLO("黄色", 4);  
    
	// 成员变量  
    private String name;
    private int index;
    
    // 构造方法 ,强制私有的
    private ColorEnum(String name, int index) {  
        this.name = name;  
        this.index = index;  
    }
    
    // 普通方法  
    public static String getName(int index) {  
    	for (ColorEnum c : ColorEnum.values()) {  
            if (c.getIndex() == index) {  
                return c.name;  
            }  
        }  
        return null;  
    } 
    
    public static ColorEnum from(int index) {
    	for (ColorEnum c : ColorEnum.values()) {
    		if (c.getIndex() == index) {  
                return c;  
            } 
		}
		return null;
	}
    
    // get set 方法  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public int getIndex() {  
        return index;  
    }  
    public void setIndex(int index) {  
        this.index = index;  
    }  
    
    @Override  
    public String toString() {  
        return this.name;  
    } 
}
