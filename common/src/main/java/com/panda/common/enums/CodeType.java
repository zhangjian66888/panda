package com.panda.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * com.panda.common.enums.CodeType
 * <p>
 * DATE 2019/6/24
 *
 * @author zhanglijian.
 */
@Getter
@AllArgsConstructor
public enum  CodeType {

    APP("app"),
    ENV("env");

    private String value;
}
