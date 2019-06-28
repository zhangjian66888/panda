package com.panda.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Description
 * <p>
 * DATE 2018/4/16.
 *
 * @author zhanglijian.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum PermissionMethod {

    ALL(0,"ALL"),
    GET(1,"GET"),
    POST(2,"POST"),
    PUT(3,"PUT"),
    DELETE(4,"DELETE");

    private int id;
    private String value;

}
