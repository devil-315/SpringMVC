package com.devil.util;

import com.devil.entity.enums.VerifyRegexEnum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName：VerifyUtils
 *
 * @author: Devil
 * @Date: 2024/9/12
 * @Description:
 * @version: 1.0
 */

//校验工具类
public class VerifyUtils {

    /*
    * regex ：规则
    * value： 要校验的字符串
    * */

    /*
     * @Author Devil
     * @Description //TODO
     *
     * @Param
     * @param regex
     * @param value
     * @return
     * @return boolean
     **/
    public static boolean verify(String regex,String value){
        if(StringTools.isEmpty(value)){
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean verify(VerifyRegexEnum regexEnum,String value){
        return verify(regexEnum.getRegex(),value);
    }
}
