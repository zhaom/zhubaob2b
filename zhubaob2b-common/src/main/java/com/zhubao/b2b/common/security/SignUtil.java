package com.zhubao.b2b.common.security;


import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class SignUtil {
    public static String genSign(String tranData, String appkey) {
        String[] array = new String[]{appkey, tranData};
        Arrays.sort(array);
        String actualSignature = Hex.encodeHexString(DigestUtils.sha(StringUtils.arrayToDelimitedString(array, "")));
        return actualSignature;
    }

    public static void main(String[] args){
        String tranData = "567889890----jjyho";
        String appkey = "jkdfkdfkdsfd'f;ll;f";
        System.out.println("actualSignature-->"+SignUtil.genSign(tranData, appkey));
    }
}
