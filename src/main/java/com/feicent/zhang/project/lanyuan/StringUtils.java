package com.feicent.zhang.project.lanyuan;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	public static final String	PERCENT	= "@percent@";

	public static List<String> string2DistinctIds(String str, String seperator)
	{
		List<String> idList = new ArrayList<String>();
		String[] ids = StringUtils.string2Array(str, seperator);
		for (int i = 0; i < ids.length; i++)
		{
			if (StringUtils.isNotEmpty(ids[i]) && !StringUtils.exists(idList, ids[i]))
			{
				idList.add(ids[i]);
			}
		}
		return idList;
	}

	public static List<String> array2List(String[] strs)
	{
		List<String> result = new ArrayList<String>(0);
		try
		{
			for (int i = 0; i < strs.length; i++)
			{
				result.add(strs[i]);
			}
		}
		catch (Exception e)
		{
		}
		return result;
	}

	public static String[] string2Array(String str, String seperator)
	{
		String[] result = new String[0];
		try
		{
			if (!str.trim().equalsIgnoreCase(""))
			{
				result = str.split(seperator);
			}
		}
		catch (Exception e)
		{
		}
		return result;
	}

	public static List<String> string2List(String str, String seperator)
	{
		return array2List(string2Array(str, seperator));
	}

	public static String array2String(String[] str, String seperator)
	{
		String result = "";
		if (str == null || str.length == 0)
		{
			return result;
		}
		boolean isFirst = true;
		for (int i = 0; i < str.length; i++)
		{
			if (isFirst)
			{
				isFirst = false;
			}
			else
			{
				result += seperator == null ? "" : seperator;
			}
			result += str[i] == null ? "" : str[i];

		}
		return result;
	}

	public static String list2String(List<String> strs, String seperator)
	{
		String result = "";
		if (strs == null || strs.size() == 0)
		{
			return result;
		}
		boolean isFirst = true;
		for (int i = 0; i < strs.size(); i++)
		{
			if (isFirst)
			{
				isFirst = false;
			}
			else
			{
				result += seperator == null ? "" : seperator;
			}
			result += strs.get(i) == null ? "" : strs.get(i);

		}
		return result;
	}

	public static String array2String(List<?> str, String seperator)
	{
		String result = "";
		if (str == null || str.size() == 0)
		{
			return result;
		}
		boolean isFirst = true;
		for (int i = 0; i < str.size(); i++)
		{
			if (isFirst)
			{
				isFirst = false;
			}
			else
			{
				result += seperator == null ? "" : seperator;
			}
			result += str.get(i) == null ? "" : str.get(i);

		}
		return result;
	}

	public static String replaceFirst(String orignalString, String regex, String replacement)
	{
		String result = orignalString;
		try
		{
			result = orignalString.replaceFirst(regex, replacement);
		}
		catch (Exception e)
		{
		}
		return result;
	}

	public static String object2String(Object o)
	{
		if (o == null)
		{
			return null;
		}
		return o.toString();
	}

	public static boolean isEmpty(String str)
	{
		return str == null || str.trim().length() == 0;
	}

	public static boolean isNotEmpty(String str)
	{
		return !isEmpty(str);
	}
	
	public static boolean isBlank(String str)
	{
		return isEmpty(str);
	}
	
	public static boolean isNotBlank(String str){
		return !isEmpty(str);
	}

	public static String addValueToString(String orignal, String newValue, String seperator)
	{
		String result = orignal;
		String[] array = string2Array(orignal, seperator);
		array = addValueToArray(array, newValue);
		result = array2String(array, seperator);
		return result;
	}

	public static String removeValueFromString(String orignal, String removeValue, String seperator)
	{
		String result = orignal;
		String[] array = string2Array(orignal, seperator);
		array = removeValueFromArray(array, removeValue);
		result = array2String(array, seperator);
		return result;
	}

	public static String[] addValueToArray(String[] orignal, String newValue)
	{
		String[] result = orignal;
		if (exists(orignal, newValue))
		{
			return result;
		}
		List<String> list = array2List(result);
		list.add(newValue);
		result = list2Array(list);
		return result;
	}

	public static boolean exists(String[] strs, String str)
	{
		boolean exists = false;
		for (int i = 0; strs != null && i < strs.length; i++)
		{
			if (equalsIgnoreCase(strs[i], str))
			{
				exists = true;
				break;
			}
		}
		return exists;

	}

	public static boolean exists(List<?> strs, String str)
	{
		boolean exists = false;
		for (int i = 0; strs != null && i < strs.size(); i++)
		{
			if (equalsIgnoreCase(strs.get(i) == null ? null : strs.get(i).toString(), str))
			{
				exists = true;
				break;
			}
		}
		return exists;

	}

	public static boolean isContains(List<?> strs, String str)
	{
		boolean exists = false;
		for (int i = 0; strs != null && i < strs.size(); i++)
		{
			if (contains(strs.get(i) == null ? null : strs.get(i).toString(), str))
			{
				exists = true;
				break;
			}
		}
		return exists;

	}
	
	@SuppressWarnings("unchecked")
	public static List<?> remove(List<?> strs, String str)
	{
		if (strs == null || strs.size() == 0)
		{
			return strs;
		}
		@SuppressWarnings("rawtypes")
		List newStrs = new ArrayList();
		for (int i = 0; i < strs.size(); i++)
		{
			if (!StringUtils.equalsIgnoreCase(strs.get(i), str))
			{
				newStrs.add(strs.get(i));
			}
		}
		return newStrs;
	}

	public static boolean include(String str, String substr, String seperator)
	{
		String[] strs = string2Array(str, seperator);
		return exists(strs, substr);
	}

	public static String[] removeValueFromArray(String[] orignal, String oldValue)
	{
		String[] result = orignal;
		List<String> list = array2List(result);
		int i = list.size() - 1;
		while (i >= 0)
		{
			String item = list.get(i) == null ? null : list.get(i).toString();
			if (equalsIgnoreCase(item, oldValue))
			{
				list.remove(i);
			}
			i--;
		}
		result = list2Array(list);
		return result;
	}

	public static boolean equalsIgnoreCase(String str1, String str2)
	{
		boolean result = false;

		if (str1 == null && str2 == null)
		{
			result = true;
		}
		if (str1 != null && str2 != null && str1.trim().equalsIgnoreCase(str2.trim()))
		{
			result = true;
		}
		return result;

	}

	public static boolean contains(String str1, String str2)
	{
		boolean result = false;

		if (str1 == null && str2 == null)
		{
			result = true;
		}
		if (str1 != null && str2 != null && str2.trim().toLowerCase().contains(str1.trim().toLowerCase()))
		{
			result = true;
		}
		return result;

	}
	
	public static boolean equals(String str1, String str2)
	{
		boolean result = false;

		if (str1 == null && str2 == null)
		{
			result = true;
		}
		if (str1 != null && str2 != null && str1.trim().equals(str2.trim()))
		{
			result = true;
		}
		return result;

	}
	
	
	public static boolean equals(Integer str1, Integer str2)
	{
		boolean result = false;

		if (str1 == null && str2 == null)
		{
			result = true;
		}
		if (str1 != null && str2 != null && str1.intValue()==(str2.intValue()))
		{
			result = true;
		}
		return result;

	}

	public static boolean equalsIgnoreCase(Object str1, Object str2)
	{
		return equalsIgnoreCase((str1 == null ? null : str1.toString()), (str2 == null ? null : str2.toString()));
	}

	public static String[] list2Array(List<String> list)
	{
		String[] result = new String[0];
		try
		{
			result = (String[]) list.toArray(new String[list.size()]);
		}
		catch (Exception e)
		{
		}
		return result;

	}

	public static boolean validValue(Object strValue)
	{
		boolean result = false;
		if (strValue == null || strValue.toString().trim().equalsIgnoreCase(""))
		{
			return result;
		}
		try
		{
			Float.valueOf(strValue.toString());
			result = true;
		}
		catch (Exception e)
		{
		}
		return result;
	}

	public static boolean validDecimal(Object strValue)
	{
		boolean result = false;
		if (strValue == null || strValue.toString().trim().equalsIgnoreCase(""))
		{
			return result;
		}
		try
		{
			strValue = strValue.toString().replaceAll(",", "");
			Double.valueOf(strValue.toString());
			result = true;
		}
		catch (Exception e)
		{
		}
		return result;
	}

	public static boolean validNumber(Object strNumber)
	{
		try
		{
			Long.valueOf(strNumber.toString());
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public static String getFixedLengthDigit(int i, int length)
	{
		int l = (i + "").length();
		String result = "";
		for (int d = 0; d < length - l; d++)
		{
			result += "0";
		}
		result += i;
		return result;
	}

	public static String getChineseNumberString(int number)
	{
		if (number == 2)
		{
			return "两";
		}
		final String[] chnCharators = new String[]
		{ "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		final String shi = "十";
		final String bai = "百";
		final String qian = "千";
		final String wan = "万";
		final String yi = "亿";
		final String zao = "兆";
		String result = "";
		List<Integer> segments = new ArrayList<Integer>();
		int n = number;
		while (n != 0)
		{
			segments.add(n % 10000);
			n = n / 10000;
		}
		for (int i = segments.size() - 1; i >= 0; i--)
		{
			int seg = segments.get(i);
			List<Integer> nums = new ArrayList<Integer>();
			int s = seg;
			while (s != 0)
			{
				nums.add(s % 10);
				s = s / 10;
			}
			boolean needZero = segments.size() > 1 && i < segments.size() - 1;
			if (nums.size() == 0)
			{
				result += chnCharators[0];
			}
			else if (nums.size() == 1)
			{
				if (needZero)
				{
					result += chnCharators[0];
				}
				result += chnCharators[nums.get(0)];
			}
			else if (nums.size() == 2)
			{
				if (needZero)
				{
					result += chnCharators[0];
				}
				if (nums.get(1) == 1 && !needZero)
				{
					result += shi;
				}
				else
				{
					result += chnCharators[nums.get(1)] + shi;
				}
				if (nums.get(0) != 0)
				{
					result += chnCharators[nums.get(0)];
				}
			}
			else if (nums.size() == 3)
			{
				if (needZero)
				{
					result += chnCharators[0];
				}
				result += chnCharators[nums.get(2)] + bai;
				if (nums.get(1) != 0)
				{
					result += chnCharators[nums.get(1)] + shi;
					if (nums.get(0) != 0)
					{
						result += chnCharators[nums.get(0)];
					}
				}
				else
				{
					if (nums.get(0) != 0)
					{
						result += chnCharators[0];
						result += chnCharators[nums.get(0)];
					}
				}
			}
			else if (nums.size() == 4)
			{
				result += chnCharators[nums.get(3)] + qian;
				if (nums.get(2) != 0)
				{
					result += chnCharators[nums.get(2)] + bai;
					if (nums.get(1) != 0)
					{
						result += chnCharators[nums.get(1)] + shi;
						if (nums.get(0) != 0)
						{
							result += chnCharators[nums.get(0)];
						}
					}
					else
					{
						if (nums.get(0) != 0)
						{
							result += chnCharators[0] + chnCharators[nums.get(0)];
						}
					}
				}
				else
				{
					if (nums.get(1) != 0)
					{
						result += chnCharators[0] + chnCharators[nums.get(1)] + shi;
					}
					else
					{
						if (nums.get(0) != 0)
						{
							result += chnCharators[0] + chnCharators[nums.get(0)];
						}
					}
				}

			}
			if (i == 1)
			{
				result += wan;
			}
			else if (i == 2)
			{
				result += yi;
			}
			else if (i == 3)
			{
				result += zao;
			}
		}
		return result;
	}

	public static String getNumbersForShort(String content)
	{
		if (StringUtils.isEmpty(content))
			return "";
		content = content.replaceAll("[\t\n\r\f]", "");
		int max = 40;
		return content.length() > max ? content.subSequence(0, max) + "..." : content; // description
	}

	public static String getDecimalFormat(Object value, String pattern)
	{
		String result = null;
		DecimalFormat myformat = new DecimalFormat();
		// "##,###.0"
		myformat.applyPattern(pattern);
		try
		{
			value = value.toString().replaceAll(",", "");
			result = myformat.format(Double.parseDouble(value.toString()));
		}
		catch (Exception e)
		{
			return value == null ? "-" : value.toString();
		}
		if (result != null && result.toString().startsWith("."))
		{
			result = "0" + result;
		}
		if (result != null && result.toString().startsWith("-."))
		{
			result = result.replaceFirst("-.", "-0.");
		}
		return result;
	}

	public static String firstLetterUpperCase(String str)
	{
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}


	public static String formatNumber(int num, int bit)
	{
		String number = "" + num;
		if (number.length() >= bit)
		{
			return number;
		}
		for (int i = bit - 1; i > 0; i--)
		{
			if (num < Math.pow(10, i))
			{
				number = "0" + number;
			}
			else
			{
				break;
			}
		}
		return number;
	}

	public static String getSubStringByPattern(String str, String pattern)
	{
		String result = null;
		try
		{
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(str);
			while (m.find())
			{
				result = m.group();
				break;
			}
		}
		catch (Exception e)
		{
		}
		return result;
	}

	public static String genCloneMethods(Class<?> clazz)
	{
		String result = "";
		Field[] fields = clazz.getDeclaredFields();
		result += (clazz.getSimpleName() + " obj=new " + clazz.getSimpleName() + "();\n");
		for (int i = 0; i < fields.length; i++)
		{
			result += ("obj.set" + StringUtils.firstLetterUpperCase(fields[i].getName()) + "(" + fields[i].getName() + ");\n");
		}
		result += ("return obj;\n");
		return result;
	}

	public static void main(String[] args)
	{
		System.out.println(getFixedLengthDigit(1, 4));
		System.out.println(getFixedLengthDigit(45678, 4));
		getChineseNumberString(1);
		getChineseNumberString(20);
		getChineseNumberString(300);
		getChineseNumberString(4000);
		for (int i = 0; i < 20000; i++)
		{
			System.out.println(i + ":" + getChineseNumberString(i));
		}
		//System.out.println(genCloneMethods(com.akazam.telewifi.sales.model.OrderBusiness.class));
		System.out.println(correctString("abcef123322"));
		System.out.println(correctString("中国;人"));
		System.out.println(correctString("中国；人"));
		System.out.println(correctString("中国?；人"));
		System.out.println(StringUtils.validateEmailAddress("<xtian@akazam.com>,jolin55@126.com "));
	}

	public static String strReplace(String c, String source, String value)
	{
		if (value != null && value.indexOf("$") == -1 && value.indexOf("\\") == -1)
		{
			return c.replaceAll(source, value);
		}
		if (StringUtils.equals(source, value))
		{
			return c;
		}
		StringBuffer content = new StringBuffer(c);
		if ((source != null) && (value != null))
		{
			int begin = content.indexOf(source);
			while (begin > 0)
			{
				content = content.replace(begin, begin + source.length(), value);
				begin = content.indexOf(source);
			}
		}
		return content.toString();
	}

	public static final boolean isChinese(char c)
	{
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)
		{
			return true;
		}
		return false;
	}

	public static final boolean isChinese(String strName)
	{
		if (StringUtils.isEmpty(strName))
			return false;
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++)
		{
			char c = ch[i];
			if (isChinese(c))
			{
				return true;
			}
		}
		return false;
	}

	public static boolean validateEmailAddress(String email)
	{
		if (StringUtils.isNotEmpty(email))
		{
			if (email.indexOf("@") > 0)
			{
				String realemail = email.trim();
				final int start = email.lastIndexOf("<");
				final int end = email.lastIndexOf(">");
				if(end>0&&!realemail.endsWith(">"))
				{
					return false;
				}
				if (start >= 0 && end >= 0 && start < end)
				{
					realemail = email.substring(start + 1, end);
				}
				realemail = realemail.trim();
				String subStringByPattern = StringUtils.getSubStringByPattern(realemail, "[\\w\\-\\.]{1,}[@][\\w\\-]{1,}([\\.]([\\w\\-]{1,})){1,3}");
				return StringUtils.isNotEmpty(subStringByPattern) && realemail.equals(subStringByPattern);
			}
		}
		return false;
	}

	public static String getEncodeName(String filename)
	{
		String result = filename;
		if (StringUtils.isNotEmpty(filename))
		{
			try
			{
				result = java.net.URLEncoder.encode(filename, "utf-8");
				return "@ENC0@" + result;
			}
			catch (Exception e)
			{
			}
		}
		return result;
	}

	public static int[] parseTerms(String serviceterm)
	{
		int year = 0;
		int month = 0;
		int day = 0;
		if (StringUtils.isNotEmpty(serviceterm))
		{
			String[] terms = serviceterm.trim().toUpperCase().split(",");
			for (int i = 0; terms != null && i < terms.length; i++)
			{
				if (terms[i].indexOf("Days") > -1)
				{
					String d = terms[i].replace("Days", "").trim();
					try
					{
						day = Double.valueOf(d).intValue();
					}
					catch (Exception e)
					{
					}
				}
				else if (terms[i].indexOf("M") > -1)
				{
					String m = terms[i].replace("M", "").trim();
					try
					{
						month = Double.valueOf(m).intValue();
					}
					catch (Exception e)
					{
					}
				}
				else if (terms[i].indexOf("Y") > -1)
				{
					String y = terms[i].replace("Y", "");
					try
					{
						year = Double.valueOf(y).intValue();
					}
					catch (Exception e)
					{
					}
				}
			}
		}
		return new int[]
		{ year, month, day };
	}

	public static String correctString(String str)
	{if(StringUtils.isEmpty(str))
	    {
	    	return str;
	    }	
	    CharsetDecoder utf8Decoder = Charset.forName("UTF-8").newDecoder();
		utf8Decoder.onMalformedInput(CodingErrorAction.REPORT);
		utf8Decoder.onUnmappableCharacter(CodingErrorAction.REPORT);
		char[] chars = str.trim().toCharArray();
		String s = "";
		for (int i = 0; i < chars.length; i++)
		{
			try
			{
				byte[] bs = (chars[i] + "").getBytes();
				if(bs.length==1)
				{
					if(bs[0]!=63)
					{
						s+=chars[i];
					}
				}
				else if(bs.length==2)
				{
					ByteBuffer bytes = ByteBuffer.wrap(bs);
					bytes.flip();
					utf8Decoder.decode(bytes).toString();
					s+=chars[i];
				}
			}
			catch (CharacterCodingException e)
			{
			}
		}
		return s;
	}
	
	
//	public static String genRandomCode(int length)
//	{
//		Random random = new Random();
//		String str="";
//		for(int i=0;i<length;i++)
//		{
//			str+=random.nextInt(10);
//		}
//		return str;
//	}
	
	public static String genRandomCode(int length)
	{
		char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();  
		Random r = new Random();
		int index, len = ch.length;
		StringBuffer sb = new StringBuffer();  
	    for (int i = 0; i < length; i++) {  
	        index = r.nextInt(len);  
	        sb.append(ch[index]);  
	    }
	    //System.out.println("验证码："+sb.toString());
	    return sb.toString().toLowerCase();
	}
	
	/**
	 * 过滤非法字符
	 * */
	public static String filterContent(String content){
		content=content.replaceAll("<","");
		content=content.replaceAll(">","");
		content=content.replaceAll("javascript:","");
		content=content.replaceAll("jscript:","");
		content=content.replaceAll("vbscript:","");
		content=content.replaceAll("&","");
		content=content.replaceAll("\"","");
		return content;
	}
	
	/**
	 * 过滤非法字符
	 * */
	public static boolean illegalContent(String content){
		if(isEmpty(content)){
			return false;
		}
		if(content.indexOf("<")>=0||content.indexOf(">")>=0
		||content.indexOf("javascript:")>=0
		||content.indexOf("jscript:")>=0
		||content.indexOf("vbscript:")>=0
		||content.indexOf("&")>=0
		||content.indexOf("\"")>=0){
			return true;
		}
		return false;
	}
}
