package com.panda.core.controller;


import com.panda.core.consts.CoreConst;
import com.panda.core.dto.PandaBusinessLineDto;
import com.panda.core.dto.search.PandaBusinessLineSo;
import com.panda.core.entity.PandaBusinessLine;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-21
 */
@RestController
@RequestMapping(CoreConst.MAIN_REQUEST_PREFIX + "businessline")
public class PandaBusinessLineController extends
        BaseController<PandaBusinessLine, PandaBusinessLineDto, PandaBusinessLineSo> {
}
