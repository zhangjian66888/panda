package com.panda.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * com.panda.api.dto.PermissionDto
 * <p>
 * DATE 2019/7/24
 *
 * @author zhanglijian.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResource {

    private String name;

    private String showName;

    private String url;

    private String method;

    private Integer type;

    private String action;

    private Integer menuType;

    private Long parentId;

    private String menuIcon;

    private Integer menuSequence;
}
