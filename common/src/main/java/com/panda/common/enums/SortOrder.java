package com.panda.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * com.panda.common.enums.JDBCOrder
 * <p>
 * DATE 2019/6/5
 *
 * @author zhanglijian.
 */
@Getter
@AllArgsConstructor
public enum SortOrder {

    ASC("ascending"),
    DESC("descending");

    private String value;

}
