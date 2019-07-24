package com.panda.common.exception;

/**
 * com.panda.common.exception.AuthException
 * <p>
 * DATE 2019/7/24
 *
 * @author zhanglijian.
 */
public class AuthException extends PandaException {

    public AuthException(Exception e) {
        super(e);
    }

    public AuthException(String msg) {
        super(msg);
    }

    public AuthException(String msg, Exception e) {
        super(msg, e);
    }
}
