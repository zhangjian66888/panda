package com.panda.common.util;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * com.panda.common.util.PathUtil
 * <p>
 * DATE 2019/7/25
 *
 * @author zhanglijian.
 */
public class PathUtil {

    public static String urlJoin(String... params) {
        String url = Arrays.stream(params).filter(t -> StringUtils.hasLength(t) && !t.equals("/"))
                .map(t -> {
                    t = t.endsWith("/") ? t : t.concat("/");
                    t = t.startsWith("/") ? t.substring(1) : t;
                    return t;
                }).collect(Collectors.joining());
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }

    public static void main(String[] args) {
        System.out.println(urlJoin("http://localhost:9090/","/","","/panda/core/","/login"));
    }
}
