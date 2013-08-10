package com.my.iud.util;

public class StringUtil {
	
	public static int[] convertStringToIntArray(String s,String split){
		String[] sArray = s.split(split);
		int length = sArray.length;
		int[] iArray = new int[length];
        for(int i = 0 ;i < length; i++){
        	iArray[i] = Integer.parseInt(sArray[i]);
        }
        return iArray;
	}
}
