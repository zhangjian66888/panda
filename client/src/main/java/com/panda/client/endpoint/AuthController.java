package com.panda.client.endpoint;

import com.panda.api.dto.AuthUser;
import com.panda.client.consts.AuthConst;
import com.panda.client.filter.AuthUserContext;
import com.panda.client.handler.RoleHandler;
import com.panda.common.dto.StatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.panda.client.controller.AuthController
 * <p>
 * DATE 2019/7/25
 *
 * @author zhanglijian.
 */
@RestController
@RequestMapping(AuthConst.AUTH_REQUEST_PREFIX)
public class AuthController {

    @Autowired
    private RoleHandler roleHandler;

    @RequestMapping(value = "permissions", method = RequestMethod.GET)
    public StatusDto permissions() {
        AuthUser user = AuthUserContext.getContext();
        return StatusDto.SUCCESS().setData(roleHandler.permissionsByRoleIds(user.getRoleIds(), user.getSuperman()));
    }
}
