package com.panda.common.exception;

/**
 * com.panda.common.exception.AuthException
 * <p>
 * DATE 2019/7/24
 *
 * @author zhanglijian.
 */
public class AuthException extends PandaFilterException {

    public AuthException(int status, String msg) {
        super(status, msg);
    }
}
