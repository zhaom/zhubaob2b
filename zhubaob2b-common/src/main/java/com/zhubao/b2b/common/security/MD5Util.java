package com.zhubao.b2b.common.security;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class MD5Util {
    private static MessageDigest md5;

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("no such md5 algorithm!", e);
        }

    }
    private static char hexDigits[] = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',  'e', 'f'};


    public static String hexString(byte[] source) {
        byte[] bs = md5.digest(source);
        char str[] = new char[16 * 2];
        int k = 0;
        for (int i = 0; i < 16; i++) {
            byte byte0 = bs[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }

    public static String hexString(String source, Charset charset) {
        String md5Str = "";
        md5Str = hexString(source.getBytes(charset));
        return md5Str;
    }

    public static void main(String[] args) {
        String uid = "blog.aaa.com";
        String aa = Long.toHexString(new Date().getTime());
        System.out.println("   str:"+aa);

        String sig = MD5Util.hexString(uid+aa,Charset.forName("UTF-8"));
        System.out.println("   sig:"+sig);
        String seed = sig.substring(30);
        System.out.println("  seed:"+seed);
        int index  = Integer.parseInt(seed,16)%aa.length();
        System.out.println(" index:"+index);
        String token = sig.substring(0,index)+aa+sig.substring(index);
        System.out.println(" token:"+token);


        int tokenLen = token.length();
        int dtLen = tokenLen - 32;
        int index1  = Integer.parseInt(token.substring(tokenLen - 2),16) % dtLen;
        String newToken = token.substring(0, index1) + token.substring(index1 + dtLen);
        String dtStr = token.substring(index1, index1+dtLen);
        System.out.println("token length:"+tokenLen);
        System.out.println("dtlen :"+dtLen);
        System.out.println("index1:"+index1);
        System.out.println("newtoke:"+newToken);
        System.out.println("dtStr:"+dtStr);

        System.out.println("newToken:"+newToken+"  man:"+MD5Util.hexString(uid + dtStr, Charset.forName("UTF-8")));

    }
}
