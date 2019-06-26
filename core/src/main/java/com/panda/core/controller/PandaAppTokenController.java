package com.panda.core.controller;


import com.panda.core.consts.CoreConst;
import com.panda.core.dto.PandaAppSecretDto;
import com.panda.core.dto.search.PandaAppSecretSo;
import com.panda.core.entity.PandaAppSecret;
import org.springframework.stereotype.Controller;
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
@Controller
@RestController
@RequestMapping(CoreConst.MAIN_REQUEST_PREFIX + "app/token")
public class PandaAppTokenController extends BaseController<PandaAppSecret, PandaAppSecretDto, PandaAppSecretSo> {

}
