package com.panda.core.api;

import com.google.common.collect.Lists;
import com.panda.api.dto.AuthResource;
import com.panda.api.dto.AuthUser;
import com.panda.api.open.AuthApi;
import com.panda.common.dto.ResultDto;
import com.panda.common.exception.AuthException;
import com.panda.core.dto.PandaPermissionDto;
import com.panda.core.handler.PermissionHandler;
import com.panda.core.handler.RoleHandler;
import com.panda.core.handler.UserHandler;
import com.panda.core.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * com.panda.core.api.AuthController
 * <p>
 * DATE 2019/7/24
 *
 * @author zhanglijian.
 */
@RestController
public class AuthController extends ApiBaseController implements AuthApi {

    @Autowired
    private UserHandler userHandler;

    @Autowired
    private PermissionHandler permissionHandler;

    @Autowired
    private RoleHandler roleHandler;

    @Override
    public ResultDto<AuthUser> userByToken(
            @RequestHeader("appCode") Long appCode,
            @RequestHeader("secret") String secret,
            @RequestHeader("profile") String profile,
            @RequestParam("token") String token) {
        verifyApp(appCode, secret, profile);
        SecurityUser securityUser = userHandler.verifyToken(token, appCode, profiles(profile));
        if (Objects.isNull(securityUser)) {
            throw new AuthException("用户token认证失败");
        }
        AuthUser authUser = AuthUser.builder()
                .userId(securityUser.getUserId())
                .username(securityUser.getUsername())
                .zhName(securityUser.getZhName())
                .roleIds(securityUser.getRoleIds())
                .superman(securityUser.getSuperman())
                .build();
        return ResultDto.SUCCESS().setData(authUser);
    }

    @Override
    public ResultDto<List<AuthResource>> resources(
            @RequestHeader("appCode") Long appCode,
            @RequestHeader("secret") String secret,
            @RequestHeader("profile") String profile) {
        verifyApp(appCode, secret, profile);

        List<PandaPermissionDto> permissions = permissionHandler.resources(appCode);
        List<AuthResource> resources = Optional.ofNullable(permissions)
                .orElse(Lists.newArrayList()).stream()
                .map(t -> AuthResource.builder()
                        .resourceId(t.getId())
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
        return ResultDto.SUCCESS().setData(resources);

    }

    @Override
    public ResultDto<Map<Long, Set<Long>>> roles(
            @RequestHeader("appCode") Long appCode,
            @RequestHeader("secret") String secret,
            @RequestHeader("profile") String profile) {
        verifyApp(appCode, secret, profile);

        return ResultDto.SUCCESS().setData(roleHandler.rolePermissions(appCode, profiles(profile)));
    }
}
