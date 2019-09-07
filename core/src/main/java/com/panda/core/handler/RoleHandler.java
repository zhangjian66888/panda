package com.panda.core.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.panda.common.enums.MenuType;
import com.panda.common.enums.PermissionType;
import com.panda.core.config.ConfigProperties;
import com.panda.core.dto.PandaPermissionDto;
import com.panda.core.dto.PandaRolePermissionDto;
import com.panda.core.dto.ResourceDto;
import com.panda.core.security.GrantedAuthority;
import com.panda.core.service.IPandaEnvService;
import com.panda.core.service.IPandaPermissionService;
import com.panda.core.service.IPandaRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
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
public class RoleHandler {


    @Autowired
    private IPandaRoleService iPandaRoleService;

    @Autowired
    private IPandaPermissionService iPandaPermissionService;

    @Autowired
    private IPandaEnvService iPandaEnvService;

    @Autowired
    private ConfigProperties configProperties;

    public List<PandaPermissionDto> permissionsByRoleIds(Set<Long> roleIds, boolean superman) {
        Set<Long> permissionIds = iPandaRoleService.permissionIdsByRoleIds(roleIds);
        if (Objects.isNull(permissionIds) || permissionIds.isEmpty()) {
            return Lists.newArrayList();
        }
        if (Objects.nonNull(superman) && superman) {
            List<Long> ids = iPandaPermissionService.findIds(PandaPermissionDto.builder()
                    .appCode(configProperties.getAppCode()).type(PermissionType.GENERAL.getId()).build());
            permissionIds.addAll(ids);
        }
        List<PandaPermissionDto> permissionDtos = iPandaPermissionService.findListByIds(permissionIds);
        return permissionDtos;
    }

    public List<GrantedAuthority> authoritiesByRoleIds(Set<Long> roleIds, boolean superman) {

        List<PandaPermissionDto> permissionDtos = permissionsByRoleIds(roleIds, superman);

        return Optional.ofNullable(permissionDtos).orElse(Lists.newArrayList())
                .stream().map(t -> GrantedAuthority.builder()
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

    public ResourceDto resourcesByRoleIds(Set<Long> roleIds, boolean superman) {

        List<PandaPermissionDto> permissionDtos = permissionsByRoleIds(roleIds, superman);
        if (Objects.isNull(permissionDtos) || permissionDtos.isEmpty()) {
            return new ResourceDto();
        }

        Map<Long, ResourceDto.MenuItemDto> menuMap = Maps.newHashMap();
        List<ResourceDto.MenuItemDto> subMenus = Lists.newArrayList();
        List<String> persmissons = Lists.newArrayList();
        for (PandaPermissionDto dto : permissionDtos) {
            ResourceDto.MenuItemDto menuItem = ResourceDto.MenuItemDto.builder()
                    .icon(dto.getMenuIcon())
                    .title(dto.getShowName())
                    .url(dto.getUrl())
                    .order(dto.getMenuSequence())
                    .build();
            if (MenuType.FIRST_MENU.getId() == dto.getMenuType()) {
                menuItem.setSubItem(new TreeSet<>(Comparator.comparing(ResourceDto.MenuItemDto::getOrder)));
                menuMap.put(dto.getId(), menuItem);
            } else if (MenuType.SECOND_MENU.getId() == dto.getMenuType()) {
                menuItem.setParentId(dto.getParentId());
                subMenus.add(menuItem);
            }
            persmissons.add(dto.getName());
        }
        subMenus.forEach(t -> Optional.ofNullable(menuMap.get(t.getParentId())).ifPresent(m -> m.getSubItem().add(t)));
        List<ResourceDto.MenuItemDto> menus = menuMap.values().stream()
                .sorted(Comparator.comparing(ResourceDto.MenuItemDto::getOrder))
                .collect(Collectors.toList());
        return ResourceDto.builder().permissions(persmissons)
                .menuItems(menus)
                .build();
    }

    public Map<Long, Set<Long>> rolePermissions(Long appCode, String[] profiles) {
        List<Long> envCodes = iPandaEnvService.profileToCode(profiles);
        Set<Long> roleIds = iPandaRoleService.roleIds(envCodes, appCode);
        if (Objects.isNull(roleIds) || roleIds.isEmpty()) {
            return Maps.newHashMap();
        }
        Set<PandaRolePermissionDto> permissions = iPandaRoleService.permissionsByRoleIds(roleIds);
        Map<Long, Set<Long>> map = Maps.newHashMap();
        for (PandaRolePermissionDto dto : permissions) {
            Set<Long> tmp = map.get(dto.getRoleId());
            if (Objects.isNull(tmp)) {
                tmp = new HashSet<>();
                map.put(dto.getRoleId(), tmp);
            }
            tmp.add(dto.getPermissionId());
        }
        return map;
    }

}
