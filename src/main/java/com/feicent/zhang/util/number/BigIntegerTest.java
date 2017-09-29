package com.feicent.zhang.util.number;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigIntegerTest {

    public static void main(String[] args) {
        float f1 = 123.01f + 2.01f;
        // 预期输出：125.02，实际输出：125.020004
        System.out.println(f1);
        // 预期输出：125.02，实际输出：125.02000000000001
        System.out.println(123.01 + 2.01);
        System.out.println("===============================");
        
        // 高精度整数测试
        BigInteger bint1 = new BigInteger("125");
        BigInteger bint2 = new BigInteger("999");
        BigInteger tmp;
        // 相加
        tmp = bint1.add(bint2);
        System.out.println("bint1 + bint2 = " + tmp);
        // 相减
        tmp = bint2.subtract(bint1);
        System.out.println("bint2 - bint1 = " + tmp);
        // 相乘
        tmp = bint1.multiply(bint2);
        System.out.println("bint1 * bint2 = " + tmp);
        // 相除
        tmp = bint2.divide(bint1);
        System.out.println("bint2 / bint1 = " + tmp);
        // 求余数
        tmp = bint2.remainder(bint1);
        System.out.println("bint2 % bint1 = " + tmp);
        // 求次方
        tmp = bint2.pow(2);
        System.out.println("bint2的二次方 = " + tmp);
        System.out.println("======================================");
        
        // 高精度小数测试
        BigDecimal bd1 = new BigDecimal(123.01);
        BigDecimal bd2 = new BigDecimal(2.01);
        BigDecimal bd;
        // 相加
        bd = bd1.add(bd2);
        System.out.println("bd1 + bd2 = " + bd);
        // 相减
        bd = bd1.subtract(bd2);
        System.out.println("bd2 - bd1 = " + bd);
        // 相乘
        bd = bd1.multiply(bd2);
        System.out.println("bd1 * bd2 = " + bd);
        // 相除
        // bd = bd1.divide(bd2);
        bd = bd1.divide(new BigDecimal(2.0));
        System.out.println("bd1 / 2.0 = " + bd);
        // 求余数
        bd = bd1.remainder(bd2);
        System.out.println("bd2 % bd1 = " + bd);
        // 求次方
        bd = bd1.pow(3);
        System.out.println("bd2的三次方 = " + bd);
        System.out.println("======================================");

        // 四舍五入保留小数位数
        BigDecimal bd3 = new BigDecimal(123.01).setScale(5,2);
        System.out.println("bd3 = " + bd3);
    }
}
