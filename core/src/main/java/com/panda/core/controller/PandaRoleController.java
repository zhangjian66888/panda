package com.panda.core.controller;


import com.panda.core.consts.CoreConst;
import com.panda.core.dto.PandaRoleDto;
import com.panda.core.dto.search.PandaRoleSo;
import com.panda.core.entity.PandaRole;
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
@RequestMapping(CoreConst.MAIN_REQUEST_PREFIX + "role")
public class PandaRoleController extends BaseController<PandaRole, PandaRoleDto, PandaRoleSo> {

}
