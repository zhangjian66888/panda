package com.panda.core.front.api;

import com.panda.core.consts.CoreConst;
import com.panda.core.dto.PandaBusinessLineDto;
import com.panda.core.dto.search.PandaBusinessLineSo;
import com.panda.core.entity.PandaBusinessLine;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.panda.core.front.PandaBusinessLineController
 * <p>
 * DATE 2019/8/1
 *
 * @author zhanglijian.
 */
@RestController
@RequestMapping(CoreConst.FRONT_REQUEST_PREFIX + "businessline")
public class FrontBusinessLineController extends BaseController<PandaBusinessLine, PandaBusinessLineDto, PandaBusinessLineSo> {
}
