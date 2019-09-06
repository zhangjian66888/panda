package com.panda.core.front.api;

import com.panda.common.dto.ResultDto;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.LoginDto;
import com.panda.core.dto.PasswdChangeDto;
import com.panda.core.handler.LoginHandler;
import com.panda.core.handler.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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
    public Mono<ResultDto> login(@Valid @RequestBody LoginDto loginDto) {
        return Mono.just(ResultDto.SUCCESS().setData(loginHandler.login(loginDto)));
    }

    @GetMapping("/user/info")
    public Mono<ResultDto> userInfo() {
        return Mono.just(ResultDto.SUCCESS().setData(userHandler.userInfo()));
    }

    @PostMapping("/change/passwd")
    @ResponseBody
    public Mono<ResultDto> updatePasswd(@RequestBody PasswdChangeDto dto) {
        userHandler.updatePasswd(dto.getOldPasswd(), dto.getNewPasswd());
        return Mono.just(ResultDto.SUCCESS());
    }

    @GetMapping("/user/resource")
    @ResponseBody
    public Mono<ResultDto> userResource() {
        return Mono.just(ResultDto.SUCCESS().setData(userHandler.userRoles()));
    }

}
