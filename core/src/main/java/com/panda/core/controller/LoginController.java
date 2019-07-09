package com.panda.core.controller;

import com.panda.common.dto.StatusDto;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.LoginDto;
import com.panda.core.handler.LoginHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class LoginController {

    @Autowired
    private LoginHandler loginHandler;

    @PostMapping("login")
    public StatusDto login(@Valid @RequestBody LoginDto loginDto) {

        return StatusDto.SUCCESS().setData(loginHandler.login(loginDto));
    }

}
