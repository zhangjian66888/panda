package com.panda.client.clients;

import com.panda.api.consts.ApiConst;
import com.panda.api.dto.AuthResource;
import com.panda.api.dto.AuthUser;
import com.panda.common.dto.ResultDto;
import feign.Param;
import feign.RequestLine;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * com.panda.client.fegin.AuthClient
 * <p>
 * DATE 2019/7/24
 *
 * @author zhanglijian.
 */
public interface AuthClient {

    @RequestLine("GET " + ApiConst.API_REQUEST_PREFIX + "user?" +
            "appCode={appCode}&secret={secret}&profile={profile}&token={token}")
    ResultDto<AuthUser> userByToken(@Param("appCode") Long appCode,
                                    @Param("secret") String secret,
                                    @Param("profile") String profile,
                                    @Param("token") String token);

    @RequestLine("GET " + ApiConst.API_REQUEST_PREFIX + "resources?" +
            "appCode={appCode}&secret={secret}&profile={profile}")
    ResultDto<List<AuthResource>> resources(@Param("appCode") Long appCode,
                                            @Param("secret") String secret,
                                            @Param("profile") String profile);

    @RequestLine("GET " + ApiConst.API_REQUEST_PREFIX + "roles?" +
            "appCode={appCode}&secret={secret}&profile={profile}")
    ResultDto<Map<Long, Set<Long>>> roles(@Param("appCode") Long appCode,
                                          @Param("secret") String secret,
                                          @Param("profile") String profile);
}
