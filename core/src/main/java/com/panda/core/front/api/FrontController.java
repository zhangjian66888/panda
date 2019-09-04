package com.panda.core.front.api;

import com.panda.common.dto.StatusDto;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.LoginDto;
import com.panda.core.dto.PasswdChangeDto;
import com.panda.core.handler.LoginHandler;
import com.panda.core.handler.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * com.panda.core.front.FrontController
 * <p>
 * DATE 2019/8/1
 *
 * @author zhanglijian.
 */
@RestController
@RequestMapping(CoreConst.FRONT_REQUEST_PREFIX + "")
public class FrontController {

    @Autowired
    private LoginHandler loginHandler;

    @Autowired
    private UserHandler userHandler;

    @PostMapping("login")
    public StatusDto login(@Valid @RequestBody LoginDto loginDto) {
        return StatusDto.SUCCESS().setData(loginHandler.login(loginDto));
    }

    @PostMapping("/change/passwd")
    @ResponseBody
    public StatusDto updatePasswd(@RequestBody PasswdChangeDto dto) {
        userHandler.updatePasswd(dto.getOldPasswd(), dto.getNewPasswd());
        return StatusDto.SUCCESS();
    }

}
