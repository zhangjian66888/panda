package com.panda.common.exception;

/**
 * com.panda.common.exception.LoginException
 * <p>
 * DATE 2019/7/8
 *
 * @author zhanglijian.
 */
public class LoginException extends PandaFilterException {

    public LoginException(int status, String msg) {
        super(status, msg);
    }

}
