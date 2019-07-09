package com.panda.core.security;

import org.springframework.util.Assert;

/**
 * com.panda.core.security.ThreadLocalContext
 * <p>
 * DATE 2019/7/8
 *
 * @author zhanglijian.
 */
public class SecurityUserContext {

    private static final ThreadLocal<SecurityUser> contextHolder = new ThreadLocal<>();

    public static void clearContext() {
        contextHolder.remove();
    }

    public static SecurityUser getContext() {
        return contextHolder.get();
    }

    public static void setContext(SecurityUser context) {
        clearContext();
        contextHolder.set(context);
    }


}
