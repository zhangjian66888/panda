package com.panda.common.util;

import com.panda.common.security.BCryptPasswordEncoder;

/**
 * com.panda.common.util.PasswordUtil
 * <p>
 * DATE 2019/7/31
 *
 * @author zhanglijian.
 */
public class PasswordUtil {

    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String encode(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public static boolean matches(String password, String encodedPassword) {
        return bCryptPasswordEncoder.matches(password, encodedPassword);
    }
}
