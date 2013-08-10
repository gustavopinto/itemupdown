/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.my.iud.util;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author dell
 */
public class ItemsCodePower {
     
    private static Map<String,String> itemcodePower;
    
    static{
        itemcodePower = new HashMap<>();
        itemcodePower.put("ts-1804021-1", "1"); //默认收费
        itemcodePower.put("ts-1804021-2", "2");  //免费收费
    }
    
    public static String getPower(String itemCode){
        if(StringUtils.isEmpty(itemCode)){
            return "1";
        }
        return itemcodePower.get(itemCode);
    }
    
}
