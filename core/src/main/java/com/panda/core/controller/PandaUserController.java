package com.panda.core.controller;


import com.panda.core.consts.CoreConst;
import com.panda.core.dto.PandaUserDto;
import com.panda.core.dto.search.PandaUserSo;
import com.panda.core.entity.PandaUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-26
 */
@RestController
@RequestMapping(CoreConst.MAIN_REQUEST_PREFIX + "user")
public class PandaUserController extends BaseController<PandaUser, PandaUserDto, PandaUserSo> {

}
