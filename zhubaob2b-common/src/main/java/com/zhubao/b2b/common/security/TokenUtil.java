package com.zhubao.b2b.common.security;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class TokenUtil {

    public static String getToken(String id) {
        String dt = Long.toHexString(new Date().getTime());
        String sig = MD5Util.hexString(id + dt, Charset.forName("UTF-8"));
        String seed = sig.substring(30);
        int index  = Integer.parseInt(seed,16) % dt.length();
        String token = sig.substring(0,index) + dt + sig.substring(index);
        return token;
    }

}
