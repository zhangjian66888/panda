package com.panda.common.util;

import com.panda.common.exception.PandaException;
import org.springframework.beans.BeanUtils;

/**
 * com.panda.common.util.BeanUtil
 * <p>
 * DATE 2019/6/4
 *
 * @author zhanglijian.
 */
public class BeanUtil {

    public static <T> T transBean(final Object src, final Class<T> clz) {
        if (src == null) {
            return null;
        } else {
            try {
                T dest = clz.newInstance();
                BeanUtils.copyProperties(src, dest);
                return dest;
            } catch (Exception e) {
                throw new PandaException(String.format("trans error : %s to %s", src.getClass().getName(), clz.getName()), e);
            }
        }
    }
}
