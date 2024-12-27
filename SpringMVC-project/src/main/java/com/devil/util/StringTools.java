package com.devil.util;

/**
 * ClassName：StringTools
 *
 * @author: Devil
 * @Date: 2024/9/12
 * @Description:
 * @version: 1.0
 */
public class StringTools {
    public static boolean isEmpty(String str){
        if(null == str || "".equals(str) ||"\u0000".equals(str)){
            return true;
        } else if ("".equals(str.trim())) {
            return true;
        }
        return false;
    }
}
