package com.feicent.zhang.base.innerclass;

/**
 * 方法内部类
 * 语法:内部类定义在一个方法体中
 * @author yzuzhang
 */
public class Outer3 {
    private int a = 100;
    
    /*
     * 方法内部类操作的并不是外部的变量，而是它自己的实例变量
     * 方法内部类可以访问方法中的参数和局部变量，这是通过在构造方法中传递参数来实现的
     * 方法内部类还可以直接访问方法的参数和方法中的局部变量，不过，这些变量必须被声明为final
     */
    public void test(final int param){
        final String str = "hello";
        class Inner {
            public void innerMethod(){
                System.out.println("方法内部类: 访问外部类的成员变量= " +a);
                System.out.println("访问方法的参数 param= " +param);
                System.out.println("方法中的局部变量 local var= " +str);
            }
        }
        Inner inner = new Inner();
        inner.innerMethod();
    }
}
