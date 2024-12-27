package com.devil;

import com.devil.entity.enums.VerifyRegexEnum;
import com.devil.util.VerifyUtils;
import org.junit.Test;

/**
 * ClassName：VerifyUtilsTest
 *
 * @author: Devil
 * @Date: 2024/9/12
 * @Description:
 * @version: 1.0
 */
public class VerifyUtilsTest {
    @Test
    public void test(){
        boolean verify = VerifyUtils.verify(VerifyRegexEnum.PHONE, "08815868957");
        System.out.println(verify);
    }
}
