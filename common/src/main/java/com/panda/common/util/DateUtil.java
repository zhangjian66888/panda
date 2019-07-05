package com.panda.common.util;

import java.time.LocalDateTime;

/**
 * com.panda.common.util.DateUtil
 * <p>
 * DATE 2019/7/4
 *
 * @author zhanglijian.
 */
public class DateUtil {


    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime now2 = now.plusDays(1);
        System.out.println(now);
        System.out.println(now2);
    }
}
