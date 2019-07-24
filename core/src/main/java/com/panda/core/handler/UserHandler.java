package com.panda.core.handler;

import com.google.common.collect.Sets;
import com.panda.common.exception.LoginException;
import com.panda.core.config.ConfigProperties;
import com.panda.core.dto.PandaGroupUserDto;
import com.panda.core.dto.PandaTokenDto;
import com.panda.core.dto.PandaUserDto;
import com.panda.core.security.SecurityUser;
import com.panda.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * com.panda.core.security.SecurityUserHandler
 * <p>
 * DATE 2019/7/8
 *
 * @author zhanglijian.
 */
@Component
public class UserHandler {


    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private IPandaUserService iPandaUserService;

    @Autowired
    private IPandaRoleService iPandaRoleService;

    @Autowired
    private IPandaGroupService iPandaGroupService;

    @Autowired
    private IPandaTokenService iPandaTokenService;

    @Autowired
    private IPandaEnvService iPandaEnvService;

    public SecurityUser verifyToken(String token) {
        List<Long> envCodes = iPandaEnvService.profileToCode(applicationContext.getEnvironment().getActiveProfiles());
        Long appCode = configProperties.getAppCode();
        return verifyToken(token, envCodes, appCode);
    }

    public SecurityUser verifyToken(String token, String profile, Long appCode) {
        List<Long> envCodes = iPandaEnvService.profileToCode(profile.split(","));
        return verifyToken(token, envCodes, appCode);
    }
    public SecurityUser verifyToken(String token, List<Long> envCodes, Long appCode) {
        PandaTokenDto tokenDto = iPandaTokenService.validToken(token);
        if (Objects.isNull(tokenDto)) {
            throw new LoginException(HttpServletResponse.SC_UNAUTHORIZED, "token expire");
        }
        PandaUserDto userDto = iPandaUserService.findById(tokenDto.getUserId());
        List<PandaGroupUserDto> userGroups = iPandaGroupService.groupsByUserId(userDto.getId());
        Set<Long> groupIds = Sets.newHashSet(userDto.getGroupId());
        Optional.ofNullable(userGroups).filter(t -> !t.isEmpty())
                .ifPresent(t -> groupIds.addAll(t.stream().map(st -> st.getGroupId()).collect(Collectors.toList())));

        List<Long> userRoleIds = iPandaUserService.rolesIdsByUserId(tokenDto.getUserId());
        List<Long> groupRoleIds = iPandaGroupService.roleIdsByGroupIds(groupIds);
        userRoleIds.addAll(groupRoleIds);
        Set<Long> roleIds = Sets.newHashSet();
        roleIds.addAll(userRoleIds);
        roleIds.addAll(groupRoleIds);
        roleIds = iPandaRoleService.filterRoles(roleIds, envCodes, appCode);
        return SecurityUser.builder()
                .userId(userDto.getId())
                .username(userDto.getUsername())
                .mobile(userDto.getMobile())
                .email(userDto.getEmail())
                .zhName(userDto.getZhName())
                .roleIds(roleIds)
                .build();

    }

}
