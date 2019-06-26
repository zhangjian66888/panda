package com.panda.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * com.panda.common.enums.DelState
 * <p>
 * DATE 2019/6/6
 *
 * @author zhanglijian.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum  DelState {
    NO(0, "正常"),
    YES(1, "删除");

    private int id;
    private String name;
}
