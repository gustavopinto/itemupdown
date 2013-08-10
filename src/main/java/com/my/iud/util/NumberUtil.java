package com.my.iud.util;

import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.StringUtils;

public class NumberUtil {
	
   public static Long getLongFromString(String number){
	   if(StringUtils.isNotEmpty(number) && NumberUtils.isNumber(number)){
		   return Long.valueOf(number);
	   }
	   return 0L;
   }
}
