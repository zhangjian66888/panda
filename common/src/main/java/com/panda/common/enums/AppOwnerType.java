package com.panda.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * com.panda.common.enums.AppOwnerType
 * <p>
 * DATE 2019/7/29
 *
 * @author zhanglijian.
 */

@Getter
@AllArgsConstructor
public enum AppOwnerType {

    USER(0, "用户"),
    OPERATE(1, "运营"),
    ADMIN(10, "管理员");
    private int id;
    private String label;
}
