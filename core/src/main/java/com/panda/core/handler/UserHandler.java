package com.panda.core.handler;

import com.google.common.collect.Sets;
import com.panda.common.enums.AppOwnerType;
import com.panda.common.exception.LoginException;
import com.panda.common.exception.PandaException;
import com.panda.common.security.PasswordEncoder;
import com.panda.core.config.ConfigProperties;
import com.panda.core.dto.PandaAppOwnerDto;
import com.panda.core.dto.PandaBusinessLineDto;
import com.panda.core.dto.PandaGroupDto;
import com.panda.core.dto.PandaGroupUserDto;
import com.panda.core.dto.PandaRoleDto;
import com.panda.core.dto.PandaTokenDto;
import com.panda.core.dto.PandaUserDto;
import com.panda.core.entity.PandaUser;
import com.panda.core.security.SecurityUser;
import com.panda.core.security.SecurityUserContext;
import com.panda.core.service.IPandaAppService;
import com.panda.core.service.IPandaBusinessLineService;
import com.panda.core.service.IPandaEnvService;
import com.panda.core.service.IPandaGroupService;
import com.panda.core.service.IPandaRoleService;
import com.panda.core.service.IPandaTokenService;
import com.panda.core.service.IPandaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
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
    private IPandaBusinessLineService iPandaBusinessLineService;

    @Autowired
    private IPandaGroupService iPandaGroupService;

    @Autowired
    private IPandaTokenService iPandaTokenService;

    @Autowired
    private IPandaEnvService iPandaEnvService;

    @Autowired
    private IPandaAppService iPandaAppService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public List<PandaRoleDto> userRoles() {
        SecurityUser user = SecurityUserContext.getContext();
        if (user == null) {
            throw new PandaException("请登录后重试");
        }
        PandaUserDto userDto = iPandaUserService.findById(user.getUserId());
        if (userDto == null) {
            throw new PandaException("帐号不存在");
        }

        List<PandaGroupUserDto> userGroups = iPandaGroupService.groupsByUserId(userDto.getId());
        Set<Long> groupIds = Sets.newHashSet(userDto.getGroupId());
        Optional.ofNullable(userGroups).filter(t -> !t.isEmpty())
                .ifPresent(t -> groupIds.addAll(t.stream().map(st -> st.getGroupId()).collect(Collectors.toList())));

        List<Long> userRoleIds = iPandaUserService.rolesIdsByUserId(userDto.getId());
        List<Long> groupRoleIds = iPandaGroupService.roleIdsByGroupIds(groupIds);
        userRoleIds.addAll(groupRoleIds);
        Set<Long> roleIds = Sets.newHashSet();
        roleIds.addAll(userRoleIds);
        roleIds.addAll(groupRoleIds);
        List<PandaRoleDto> roleDtos = iPandaRoleService.fillRole(roleIds);
        return roleDtos;
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

    public PandaUserDto userInfo() {
        SecurityUser user = SecurityUserContext.getContext();
        if (user == null) {
            throw new PandaException("请登录后重试");
        }
        PandaUserDto pandaUser = iPandaUserService.findById(user.getUserId());
        if (pandaUser == null) {
            throw new PandaException("帐号不存在");
        }
        pandaUser.setPassword(null);
        PandaBusinessLineDto lineDto = iPandaBusinessLineService.findById(pandaUser.getBusinessLineId());
        PandaGroupDto groupDto = iPandaGroupService.findById(pandaUser.getGroupId());
        pandaUser.setBusinessLineName(Optional.ofNullable(lineDto).map(t -> t.getBusinessLineName()).orElse("未知"));
        pandaUser.setGroupName(Optional.ofNullable(groupDto).map(t -> t.getGroupName()).orElse("未知"));
        return pandaUser;
    }

    public void updatePasswd(String oldPasswd, String newPasswd) {
        SecurityUser user = SecurityUserContext.getContext();
        if (user == null) {
            throw new PandaException("请登录后重试");
        }
        PandaUserDto pandaUser = iPandaUserService.findById(user.getUserId());
        if (pandaUser == null) {
            throw new PandaException("帐号不存在");
        }
        if (!passwordEncoder.matches(oldPasswd, pandaUser.getPassword())) {
            throw new PandaException("旧密码输入错误");
        }
        LocalDateTime now = LocalDateTime.now();
        PandaUser entity = PandaUser.builder().password(passwordEncoder.encode(newPasswd)).build();
        entity.setId(pandaUser.getId());
        entity.setUpdateTime(now);
        iPandaUserService.updateById(entity);
    }

}
