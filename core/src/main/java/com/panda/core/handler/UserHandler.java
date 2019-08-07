package com.panda.core.handler;

import com.google.common.collect.Sets;
import com.panda.common.enums.AppOwnerType;
import com.panda.common.exception.LoginException;
import com.panda.core.config.ConfigProperties;
import com.panda.core.dto.PandaAppOwnerDto;
import com.panda.core.dto.PandaGroupUserDto;
import com.panda.core.dto.PandaTokenDto;
import com.panda.core.dto.PandaUserDto;
import com.panda.core.security.SecurityUser;
import com.panda.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
    private Environment environment;

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

    @Autowired
    private IPandaAppService iPandaAppService;

    public SecurityUser verifyToken(String token) {
        List<Long> envCodes = iPandaEnvService.profileToCode(environment.getActiveProfiles());
        Long appCode = configProperties.getAppCode();
        return verifyToken(token, appCode, envCodes);
    }

    public SecurityUser verifyToken(String token, Long appCode, String[] profiles) {
        List<Long> envCodes = iPandaEnvService.profileToCode(profiles);
        return verifyToken(token, appCode, envCodes);
    }

    public SecurityUser verifyToken(String token, Long appCode, List<Long> envCodes) {
        PandaTokenDto tokenDto = iPandaTokenService.validToken(token);
        if (Objects.isNull(tokenDto)) {
            throw new LoginException(HttpServletResponse.SC_UNAUTHORIZED, "token expire");
        }
        PandaUserDto userDto = iPandaUserService.findById(tokenDto.getUserId());
        boolean superman = false;
        if (Objects.nonNull(appCode)) {
            PandaAppOwnerDto owner = iPandaAppService.findByAppCodeOwnerId(appCode, userDto.getId());
            if (Objects.nonNull(owner) && AppOwnerType.ADMIN.getId() == owner.getOwnerType()) {
                superman = true;
            }
        }

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
        roleIds = iPandaRoleService.filterRoles(roleIds, appCode, envCodes);
        return SecurityUser.builder()
                .userId(userDto.getId())
                .username(userDto.getUsername())
                .mobile(userDto.getMobile())
                .email(userDto.getEmail())
                .zhName(userDto.getZhName())
                .roleIds(roleIds)
                .superman(superman)
                .build();

    }

    public SecurityUser frontToken(String token) {
        PandaTokenDto tokenDto = iPandaTokenService.validToken(token);
        if (Objects.isNull(tokenDto)) {
            throw new LoginException(HttpServletResponse.SC_UNAUTHORIZED, "token expire");
        }
        PandaUserDto userDto = iPandaUserService.findById(tokenDto.getUserId());
        return SecurityUser.builder()
                .userId(userDto.getId())
                .username(userDto.getUsername())
                .mobile(userDto.getMobile())
                .email(userDto.getEmail())
                .zhName(userDto.getZhName())
                .build();

    }

}
