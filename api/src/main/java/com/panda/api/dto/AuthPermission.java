package com.panda.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * com.panda.api.dto.AuthMenu
 * <p>
 * DATE 2019/7/25
 *
 * @author zhanglijian.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthPermission {

    private List<String> permissions;

    private List<MenuItem> menuItems;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MenuItem {
        private String title;
        private String url;
        private String icon;

        private Long parentId;
        private Integer order;

        private Set<MenuItem> subItem;
    }
}
