package com.panda.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * com.panda.common.enums.ApplyType
 * <p>
 * DATE 2019/9/6
 *
 * @author zhanglijian.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum  ApplyType {

    DEFAULT(0,"未知"),
    ROLE(1,"权限申请");

    private int id;
    private String label;
}
