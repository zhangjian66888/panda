package com.panda.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * com.panda.common.enums.UserType
 * <p>
 * DATE 2019/6/28
 *
 * @author zhanglijian.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum UserType {


    FRONTED(0, "前台用户"),
    BACKED(1, "后台用户"),
    ALL(2, "所有权限");

    private int id;
    private String value;
}
