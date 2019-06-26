package com.panda.common.exception;

/**
 * com.panda.common.exception.PandaException
 * <p>
 * DATE 2019/6/4
 *
 * @author zhanglijian.
 */
public class PandaException extends RuntimeException {

    public PandaException(Exception e) {
        super(e);
    }

    public PandaException(String msg) {
        super(msg);
    }

    public PandaException(String msg, Exception e) {
        super(msg, e);
    }
}
