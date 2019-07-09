package com.panda.common.exception;

/**
 * com.panda.common.exception.PermissionException
 * <p>
 * DATE 2019/7/8
 *
 * @author zhanglijian.
 */
public class PermissionException extends PandaFilterException {

    public PermissionException(int status, String msg) {
        super(status, msg);
    }
}
