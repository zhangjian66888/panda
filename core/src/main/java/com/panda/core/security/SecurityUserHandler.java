package com.panda.core.security;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.panda.common.exception.LoginException;
import com.panda.core.dto.*;
import com.panda.core.service.IPandaGroupService;
import com.panda.core.service.IPandaTokenService;
import com.panda.core.service.IPandaUserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SecurityUserHandler {

    @Autowired
    private IPandaUserService iPandaUserService;

    @Autowired
    private IPandaGroupService iPandaGroupService;

    @Autowired
    private IPandaTokenService iPandaTokenService;

    public SecurityUser verifyToken(String token) {
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
