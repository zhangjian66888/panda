package com.panda.core.controller;


import com.panda.common.dto.StatusDto;
import com.panda.common.exception.PandaException;
import com.panda.common.util.BeanUtil;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.PandaCodeDto;
import com.panda.core.dto.PandaEnvDto;
import com.panda.core.dto.search.PandaCodeSo;
import com.panda.core.dto.search.PandaEnvSo;
import com.panda.core.entity.PandaCode;
import com.panda.core.entity.PandaEnv;
import com.panda.core.service.IPandaCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-21
 */
@RestController
@RequestMapping(CoreConst.MAIN_REQUEST_PREFIX + "code")
public class PandaCodeController extends BaseController<PandaCode, PandaCodeDto, PandaCodeSo> {

    @Autowired
    private IPandaCodeService iPandaCodeService;

    @GetMapping("/obtain/{type}")
    public StatusDto obtain(@PathVariable(value = "type") String type) {
        return StatusDto.SUCCESS().setData(iPandaCodeService.obtainCode(type));
    }

}
