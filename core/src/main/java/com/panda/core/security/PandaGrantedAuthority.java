package com.panda.core.security;

import lombok.*;

/**
 * com.panda.core.security.PandaGrantedAuthority
 * <p>
 * DATE 2019/7/8
 *
 * @author zhanglijian.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PandaGrantedAuthority {

    private Long id;

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
