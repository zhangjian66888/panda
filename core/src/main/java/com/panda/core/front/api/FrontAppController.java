package com.panda.core.front.api;

import com.panda.core.consts.CoreConst;
import com.panda.core.controller.BaseController;
import com.panda.core.dto.PandaAppDto;
import com.panda.core.dto.search.PandaAppSo;
import com.panda.core.entity.PandaApp;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.panda.core.front.PandaAppController
 * <p>
 * DATE 2019/8/1
 *
 * @author zhanglijian.
 */
@RestController
@RequestMapping(CoreConst.FRONT_REQUEST_PREFIX + "app")
public class FrontAppController extends BaseController<PandaApp, PandaAppDto, PandaAppSo> {
}
