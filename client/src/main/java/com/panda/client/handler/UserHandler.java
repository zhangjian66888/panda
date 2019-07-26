package com.panda.client.handler;

import com.panda.api.dto.AuthUser;
import com.panda.client.clients.AuthClient;
import com.panda.client.config.AuthProperties;
import com.panda.common.dto.ResultDto;
import com.panda.common.exception.PandaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * com.panda.client.handler.UserHandler
 * <p>
 * DATE 2019/7/25
 *
 * @author zhanglijian.
 */
@Component
public class UserHandler {

    @Autowired
    private AuthClient authClient;
    @Autowired
    private AuthProperties authProperties;

    public AuthUser verifyToken(String token) {

        ResultDto<AuthUser> result = authClient.userByToken(authProperties.getAppCode(),
                authProperties.getAppSecret(), authProperties.profile(), token);

        if (result.getCode() != ResultDto.CODE_SUCESS) {
            throw new PandaException(result.getMsg());
        }
        return result.getData();
    }

}
