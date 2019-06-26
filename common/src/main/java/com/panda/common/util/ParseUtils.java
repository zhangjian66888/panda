package com.panda.common.util;

import org.springframework.data.util.ParsingUtils;

/**
 * com.panda.common.util.ParseUtils
 * <p>
 * DATE 2019/6/24
 *
 * @author zhanglijian.
 */
public class ParseUtils {

    public static String camelToUnderline(String field) {
        return ParsingUtils.reconcatenateCamelCase(field, "_");
    }
}
