package com.util;

public class BookUtil {
	//ÅĞ¶ÏÊäÈë×Ö·û´®ÊÇ·ñÊÇÊı×Ö
	public static boolean isNumber(String str)
	{
		for(int i=str.length();--i>0;)
		{
			if(!Character.isDigit(str.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}
}
