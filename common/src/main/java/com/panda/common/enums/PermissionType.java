package com.panda.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Description
 * <p>
 * DATE 2018/5/15.
 *
 * @author zhanglijian.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum PermissionType {


    GENERAL(0, "普通功能"),
    IMPORTANT(1, "重要功能"),
    SENSITIVE(2, "敏感功能"),
    BUSINESS(3, "业务功能"),
    Beta功能(100, "Beta功能");

    private int id;
    private String label;

}
