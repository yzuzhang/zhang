package com.feicent.zhang.util.number;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 提供高精度的运算支持. 所以函数以double为参数类型，兼容int与float.
 */
public class NumberUtils
{
	
	private NumberUtils()
	{
	}

	public static byte[] toByteArray(BigInteger big)
	{
		byte[] result = new byte[0];
		if (big == null || big.compareTo(BigInteger.ZERO) < 0)
		{
			return result;
		}
		BigInteger temp = new BigInteger(big.toByteArray());
		List<Byte> list = new ArrayList<Byte>();
		while (temp.compareTo(BigInteger.ZERO) != 0)
		{

			list.add(temp.subtract(temp.shiftRight(1).shiftLeft(1)).byteValue());
			temp = temp.shiftRight(1);
		}
		result = new byte[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			result[list.size() - 1 - i] = list.get(i);
		}
		return result;
	}

	public static BigInteger toBigInteger(long[] longValues, int bitLength)
	{
		BigInteger result = null;
		if (longValues == null || longValues.length == 0 || bitLength <= 0)
			return result;
		byte[] bytes = new byte[longValues.length * bitLength];
		for (int i = 0; i < bytes.length; i++)
		{
			bytes[i] = 0;
		}
		for (int i = 0; i < bytes.length; i += bitLength)
		{
			long tempValue = longValues[i / bitLength];
			byte[] valueBytes = toByteArray(new BigInteger("" + tempValue));
			for (int j = 0; j < bitLength; j++)
			{
				if (valueBytes.length >= j + 1)
				{
					bytes[i + bitLength - j - 1] = valueBytes[valueBytes.length - j - 1];
				}
			}
		}
		String byteString = "";
		for (int i = 0; i < bytes.length; i++)
		{
			byteString = byteString + bytes[i];
		}
		return result = new BigInteger(byteString, 2);
	}

	public static long[] toLongValues(BigInteger bigIntegerValue, int bitLengthOfValue, int numOfValues)
	{
		long[] result = new long[0];
		if (bigIntegerValue == null)
		{
			return result;
		}
		int bitLength = bitLengthOfValue * numOfValues;
		if (bitLength <= 0)
		{
			return result;
		}
		result = new long[numOfValues];
		BigInteger tempBigIntegerValue = new BigInteger(bigIntegerValue.toByteArray());
		for (int i = 0; i < numOfValues; i++)
		{
			result[numOfValues - 1 - i] = tempBigIntegerValue.subtract(tempBigIntegerValue.shiftRight(bitLengthOfValue).shiftLeft(bitLengthOfValue)).longValue();
			tempBigIntegerValue = tempBigIntegerValue.shiftRight(bitLengthOfValue);
		}
		return result;
	}

	public static Long toLong(Object obj)
	{
		Long result = null;
		try
		{
			if (obj != null)
				result = Long.parseLong(obj.toString());
		}
		catch (Exception e)
		{
		}
		return result;
	}
	
	/**
	 * 精确的加法运算.
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.add(b2).doubleValue();
	}
	
	/**
	 * 
	 * 精确的减法运算.
	 * 
	 * @param v1 被减数
	 * @param v2 减数
	 */
	public static double subtract(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}
	
	/**
	 * 提供精确的乘法运算.
	 */
	public static double multiply(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).doubleValue();
	}
	
	/**
	 * 提供精确的乘法运算，并对运算结果截位.
	 * 
	 * @param scale 运算结果小数后精确的位数
	 */
	public static double multiply(double v1, double v2,int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}


	/**
	 * 提供（相对）精确的除法运算.
	 * 
	 * @see #divide(double, double, int)
	 */
	public static double divide(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算.
	 * 由scale参数指定精度，以后的数字四舍五入.
	 * 
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位
	 */
	public static double divide(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 提供精确的小数位四舍五入处理.
	 * 
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(v+ 0.0000001);
		return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 提供（相对）精确的除法运算.
	 * 由scale参数指定精度，以后的数字四舍五入.然后直接诶返回计算的百分比
	 */
	public static String divideToProfit(double v1, double v2, int scale){
		if( v2 >= 0.1 ){
			return NumberUtils.round(NumberUtils.divide(v1, v2,4)* 100,2)  + "%";
		}else{
			return NumberUtils.round(100,2)  + "%";
		}
		
	}
	
	public static void main(String[] args) {
		double d = divide(456.6, 53.5, 2);
		String str = divideToProfit(53.5, 456.6, 2);
		System.out.println(d);
		System.out.println(str);
	}
}