package com.panda.core.front.api;

import com.panda.core.consts.CoreConst;
import com.panda.core.controller.BaseController;
import com.panda.core.dto.PandaRoleDto;
import com.panda.core.dto.search.PandaRoleSo;
import com.panda.core.entity.PandaRole;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.panda.core.front.PandaRoleController
 * <p>
 * DATE 2019/8/1
 *
 * @author zhanglijian.
 */
@RestController
@RequestMapping(CoreConst.FRONT_REQUEST_PREFIX + "role")
public class FrontRoleController extends BaseController<PandaRole, PandaRoleDto, PandaRoleSo> {
}
