package com.panda.client.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.panda.api.dto.AuthPermission;
import com.panda.api.dto.AuthResource;
import com.panda.client.cache.AuthLocalCache;
import com.panda.common.enums.MenuType;
import com.panda.common.enums.PermissionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * com.panda.client.handler.RoleHandler
 * <p>
 * DATE 2019/7/25
 *
 * @author zhanglijian.
 */
@Component
public class RoleHandler {

    @Autowired
    private AuthLocalCache authLocalCache;

    public List<AuthResource> resourceByRoleIds(Set<Long> roleIds, boolean superman) {
        if (Objects.isNull(roleIds) || roleIds.isEmpty()) {
            return Lists.newArrayList();
        }
        Set<Long> ids = authLocalCache.getIdsByRoleIds(roleIds);
        if (Objects.isNull(ids) || ids.isEmpty()) {
            return Lists.newArrayList();
        }
        if (Objects.nonNull(superman) && superman) {
            Optional.ofNullable(authLocalCache.filterResourceByType(PermissionType.GENERAL.getId()))
                    .filter(t -> !t.isEmpty())
                    .ifPresent(t -> ids.addAll(t));
        }
        return authLocalCache.getResourceByIds(ids);
    }

    public AuthPermission permissionsByRoleIds(Set<Long> roleIds, boolean superman) {

        List<AuthResource> resources = resourceByRoleIds(roleIds, superman);

        Map<Long, AuthPermission.MenuItem> menuMap = Maps.newHashMap();
        List<AuthPermission.MenuItem> subMenus = Lists.newArrayList();
        List<String> persmissons = Lists.newArrayList();
        for (AuthResource dto : resources) {
            AuthPermission.MenuItem menuItem = AuthPermission.MenuItem.builder()
                    .icon(dto.getMenuIcon())
                    .title(dto.getShowName())
                    .url(dto.getUrl())
                    .order(dto.getMenuSequence())
                    .build();
            if (MenuType.FIRST_MENU.getId() == dto.getMenuType()) {
                menuItem.setSubItem(new TreeSet<>(Comparator.comparing(AuthPermission.MenuItem::getOrder)));
                menuMap.put(dto.getResourceId(), menuItem);
            } else if (MenuType.SECOND_MENU.getId() == dto.getMenuType()) {
                menuItem.setParentId(dto.getParentId());
                subMenus.add(menuItem);
            }
            persmissons.add(dto.getName());
        }
        subMenus.forEach(t -> Optional.ofNullable(menuMap.get(t.getParentId())).ifPresent(m -> m.getSubItem().add(t)));
        List<AuthPermission.MenuItem> menus = menuMap.values().stream()
                .sorted(Comparator.comparing(AuthPermission.MenuItem::getOrder))
                .collect(Collectors.toList());
        return AuthPermission.builder().permissions(persmissons)
                .menuItems(menus)
                .build();
    }

}

