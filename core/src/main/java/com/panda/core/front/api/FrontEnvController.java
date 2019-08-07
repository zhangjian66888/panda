package com.panda.core.front.api;

import com.panda.core.consts.CoreConst;
import com.panda.core.dto.PandaEnvDto;
import com.panda.core.dto.search.PandaEnvSo;
import com.panda.core.entity.PandaEnv;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.panda.core.front.PandaEnvController
 * <p>
 * DATE 2019/8/1
 *
 * @author zhanglijian.
 */
@RestController
@RequestMapping(CoreConst.FRONT_REQUEST_PREFIX + "env")
public class FrontEnvController extends BaseController<PandaEnv, PandaEnvDto, PandaEnvSo> {
}
