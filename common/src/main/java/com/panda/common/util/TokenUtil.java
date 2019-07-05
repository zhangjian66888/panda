package com.panda.common.util;

import java.util.UUID;

/**
 * com.panda.common.util.TokenUtil
 * <p>
 * DATE 2019/6/25
 *
 * @author zhanglijian.
 */
public class TokenUtil {


    public static String secret(){
        return uuid();
    }

    public static String token(){
        return uuid();
    }

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static void main(String[] args) {
        System.out.println(secret());
    }
}
