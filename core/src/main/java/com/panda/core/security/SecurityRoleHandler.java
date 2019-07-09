package com.panda.core.security;

import com.google.common.collect.Lists;
import com.panda.core.config.VariableConfig;
import com.panda.core.dto.PandaPermissionDto;
import com.panda.core.service.IPandaEnvService;
import com.panda.core.service.IPandaPermissionService;
import com.panda.core.service.IPandaRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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


}
