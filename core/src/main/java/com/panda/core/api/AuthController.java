package com.panda.core.api;

import com.panda.api.dto.AuthAuthority;
import com.panda.api.dto.AuthUser;
import com.panda.api.open.AuthApi;
import com.panda.common.dto.ResultDto;
import com.panda.common.exception.AuthException;
import com.panda.core.security.SecurityUser;
import com.panda.core.handler.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
    private UserHandler securityUserHandler;

    @Override
    public ResultDto<AuthUser> userByToken(
            @RequestHeader("appCode") Long appCode,
            @RequestHeader("secret") String secret,
            @RequestHeader("profile") String profile,
            @RequestParam("token") String token) {
        verifyApp(appCode, secret, profile);
        SecurityUser securityUser = securityUserHandler.verifyToken(token, profile, appCode);
        if (Objects.isNull(securityUser)){
            throw new AuthException("用户token认证失败");
        }
        AuthUser authUser = AuthUser.builder()
                .userId(securityUser.getUserId())
                .username(securityUser.getUsername())
                .zhName(securityUser.getZhName())
                .roleIds(securityUser.getRoleIds())
                .build();
        return ResultDto.SUCCESS().setData(authUser);
    }

    @Override
    public ResultDto<List<AuthAuthority>> authorities(
            @RequestHeader("appCode") Long appCode,
            @RequestHeader("secret") String secret,
            @RequestHeader("profile") String profile) {
        verifyApp(appCode, secret, profile);
        return null;
    }

    @Override
    public ResultDto<Map<Long, Set<Long>>> role(
            @RequestHeader("appCode") Long appCode,
            @RequestHeader("secret") String secret,
            @RequestHeader("profile") String profile) {
        verifyApp(appCode, secret, profile);
        return null;
    }
}
