package com.panda.client.filter;

import com.panda.api.dto.AuthUser;

/**
 * com.panda.core.security.ThreadLocalContext
 * <p>
 * DATE 2019/7/8
 *
 * @author zhanglijian.
 */
public class AuthUserContext {

    private static final ThreadLocal<AuthUser> contextHolder = new ThreadLocal<>();

    public static void clearContext() {
        contextHolder.remove();
    }

    public static AuthUser getContext() {
        return contextHolder.get();
    }

    public static void setContext(AuthUser context) {
        clearContext();
        contextHolder.set(context);
    }


}
