package com.panda.core.security;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.panda.common.enums.MenuType;
import com.panda.core.config.VariableConfig;
import com.panda.core.dto.PandaPermissionDto;
import com.panda.core.dto.PermissionDto;
import com.panda.core.service.IPandaEnvService;
import com.panda.core.service.IPandaPermissionService;
import com.panda.core.service.IPandaRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * com.panda.core.security.SecurityRoleHandler
 * <p>
 * DATE 2019/7/8
 *
 * @author zhanglijian.
 */
@Slf4j
@Component
public class SecurityRoleHandler {

    @Autowired
    private VariableConfig variableConfig;

    @Autowired
    private IPandaRoleService iPandaRoleService;

    @Autowired
    private IPandaPermissionService iPandaPermissionService;

    @Autowired
    private IPandaEnvService iPandaEnvService;

    public List<PandaGrantedAuthority> authoritiesByRoleIds(Collection<Long> roleIds) {

        List<Long> envCodes = iPandaEnvService.profileToCode(variableConfig.getProfile().split(","));
        List<Long> permissionIds = iPandaRoleService.permissionIdsByRoleIds(roleIds, envCodes, variableConfig.getAppCode());
        if (Objects.isNull(permissionIds) || permissionIds.isEmpty()) {
            return Lists.newArrayList();
        }
        List<PandaPermissionDto> permissionDtos = iPandaPermissionService.findListByIds(permissionIds);

        return Optional.ofNullable(permissionDtos).orElse(Lists.newArrayList())
                .stream().map(t -> PandaGrantedAuthority.builder()
                        .id(t.getId())
                        .name(t.getName())
                        .showName(t.getShowName())
                        .url(t.getUrl())
                        .method(t.getMethod())
                        .type(t.getType())
                        .action(t.getAction())
                        .menuType(t.getMenuType())
                        .parentId(t.getParentId())
                        .menuIcon(t.getMenuIcon())
                        .menuSequence(t.getMenuSequence())
                        .build())
                .collect(Collectors.toList());

    }

    public PermissionDto permissionsByRoleIds(Collection<Long> roleIds) {
        List<Long> envCodes = iPandaEnvService.profileToCode(variableConfig.getProfile().split(","));
        List<Long> permissionIds = iPandaRoleService.permissionIdsByRoleIds(roleIds, envCodes, variableConfig.getAppCode());
        if (Objects.isNull(permissionIds) || permissionIds.isEmpty()) {
            return new PermissionDto();
        }
        List<PandaPermissionDto> permissionDtos = iPandaPermissionService.findListByIds(permissionIds);

        Map<Long, PermissionDto.MenuItemDto> menuMap = Maps.newHashMap();
        List<PermissionDto.MenuItemDto> subMenus = Lists.newArrayList();
        List<String> persmissons = Lists.newArrayList();
        for (PandaPermissionDto dto : permissionDtos) {
            PermissionDto.MenuItemDto menuItem = PermissionDto.MenuItemDto.builder()
                    .icon(dto.getMenuIcon())
                    .title(dto.getShowName())
                    .url(dto.getUrl())
                    .order(dto.getMenuSequence())
                    .build();
            if (MenuType.FIRST_MENU.getId() == dto.getMenuType()) {
                menuItem.setSubItem(new TreeSet<>(Comparator.comparing(PermissionDto.MenuItemDto::getOrder)));
                menuMap.put(dto.getId(), menuItem);
            } else if (MenuType.SECOND_MENU.getId() == dto.getMenuType()) {
                menuItem.setParentId(dto.getParentId());
                subMenus.add(menuItem);
            }
            persmissons.add(dto.getName());
        }
        subMenus.forEach(t -> Optional.ofNullable(menuMap.get(t.getParentId())).ifPresent(m -> m.getSubItem().add(t)));
        List<PermissionDto.MenuItemDto> menus = menuMap.values().stream()
                .sorted(Comparator.comparing(PermissionDto.MenuItemDto::getOrder))
                .collect(Collectors.toList());
        return PermissionDto.builder().permissions(persmissons)
                .menuItems(menus)
                .build();
    }

}
