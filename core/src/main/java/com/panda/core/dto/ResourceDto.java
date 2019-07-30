package com.panda.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * com.panda.core.dto.PermissionDto
 * <p>
 * DATE 2019/7/9
 *
 * @author zhanglijian.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDto {

    private List<String> permissions;

    private List<MenuItemDto> menuItems;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MenuItemDto {
        private String title;
        private String url;
        private String icon;

        private Long parentId;
        private Integer order;

        private Set<MenuItemDto> subItem;
    }
}
