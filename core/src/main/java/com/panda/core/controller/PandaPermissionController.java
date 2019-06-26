package com.panda.core.controller;


import com.panda.core.consts.CoreConst;
import com.panda.core.dto.PandaPermissionDto;
import com.panda.core.dto.search.PandaPermissionSo;
import com.panda.core.entity.PandaPermission;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-26
 */
@RestController
@RequestMapping(CoreConst.MAIN_REQUEST_PREFIX + "permission")
public class PandaPermissionController
        extends BaseController<PandaPermission, PandaPermissionDto, PandaPermissionSo>{

}
