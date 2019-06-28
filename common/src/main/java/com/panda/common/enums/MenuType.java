package com.panda.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * com.sayabc.speed.enums.MenuTypeEnum
 * <p>
 * DATE 2018/11/24
 *
 * @author zhanglijian.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum  MenuType {

    DEFAULT(0, "默认权限"),
    FIRST_MENU(1, "一级菜单"),
    SECOND_MENU(2, "二级菜单");

    private int id;
    private String value;
}
