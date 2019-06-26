package com.panda.common.util;

import com.panda.common.exception.PandaException;

/**
 * com.panda.common.util.ClassUtil
 * <p>
 * DATE 2019/6/24
 *
 * @author zhanglijian.
 */
public class ClassUtil {

    public static  <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new PandaException(String.format("%s newInstance error", clazz.getName()), e);
        }
    }
}

