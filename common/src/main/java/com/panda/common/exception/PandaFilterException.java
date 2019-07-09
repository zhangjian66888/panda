package com.panda.common.exception;

import lombok.Data;

/**
 * com.panda.common.exception.PandaException
 * <p>
 * DATE 2019/6/4
 *
 * @author zhanglijian.
 */
@Data
public class PandaFilterException extends RuntimeException {

    private int status;

    public PandaFilterException(int status, String msg) {
        super(msg);
        this.status = status;
    }

}
