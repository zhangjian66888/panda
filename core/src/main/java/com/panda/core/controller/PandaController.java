package com.panda.core.controller;

import com.panda.common.dto.StatusDto;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.LoginDto;
import com.panda.core.handler.LoginHandler;
import com.panda.core.handler.RoleHandler;
import com.panda.core.security.SecurityUser;
import com.panda.core.security.SecurityUserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * com.panda.core.controller.LoginController
 * <p>
 * DATE 2019/7/4
 *
 * @author zhanglijian.
 */
@RestController
@RequestMapping(CoreConst.MAIN_REQUEST_PREFIX + "")
public class PandaController {

    @Autowired
    private LoginHandler loginHandler;

    @Autowired
    private RoleHandler roleHandler;

    @PostMapping("login")
    public StatusDto login(@Valid @RequestBody LoginDto loginDto) {

        return StatusDto.SUCCESS().setData(loginHandler.login(loginDto));
    }

    @GetMapping("resources")
    public StatusDto resources() {
        SecurityUser user = SecurityUserContext.getContext();
        return StatusDto.SUCCESS().setData(roleHandler.resourcesByRoleIds(user.getRoleIds(), user.getSuperman()));
    }

}
