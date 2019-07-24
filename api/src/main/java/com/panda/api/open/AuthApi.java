package com.panda.api.open;

import com.panda.api.consts.ApiConst;
import com.panda.api.dto.AuthResource;
import com.panda.api.dto.AuthUser;
import com.panda.common.dto.ResultDto;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * com.panda.api.open.AuthController
 * <p>
 * DATE 2019/7/24
 *
 * @author zhanglijian.
 */
@RequestMapping(ApiConst.API_REQUEST_PREFIX)
public interface AuthApi {

    /**
     * 根据token 获取用户信息
     *
     * @param appCode 应用编号
     * @param secret  认证密钥
     * @param profile 环境
     * @param token   用户token
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    ResultDto<AuthUser> userByToken(@RequestHeader("appCode") Long appCode,
                                    @RequestHeader("secret") String secret,
                                    @RequestHeader("profile") String profile,
                                    @RequestParam("token") String token);

    /**
     * 获取应用对应环境的权限信息
     *
     * @param appCode 应用编号
     * @param secret  认证密钥
     * @param profile 环境
     * @return
     */
    @RequestMapping(value = "/resources", method = RequestMethod.GET)
    ResultDto<List<AuthResource>> resources(
            @RequestHeader("appCode") Long appCode,
            @RequestHeader("secret") String secret,
            @RequestHeader("profile") String profile);

    /**
     * 获取所有角色对应的权限列表
     *
     * @param appCode 应用编号
     * @param secret  认证密钥
     * @param profile 环境
     * @return
     */
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    ResultDto<Map<Long, Set<Long>>> roles(
            @RequestHeader("appCode") Long appCode,
            @RequestHeader("secret") String secret,
            @RequestHeader("profile") String profile);
}
