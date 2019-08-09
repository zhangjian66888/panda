package com.panda.core.front.api;

import com.panda.common.dto.ResultDto;
import com.panda.common.dto.SelectItemDto;
import com.panda.common.dto.StatusDto;
import com.panda.common.mybatis.InPair;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.search.PandaAppSo;
import com.panda.core.service.IPandaAppService;
import com.panda.core.service.IPandaBusinessLineService;
import com.panda.core.service.IPandaEnvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * com.panda.core.front.PandaAppController
 * <p>
 * DATE 2019/8/1
 *
 * @author zhanglijian.
 */
@RestController
@RequestMapping(CoreConst.FRONT_REQUEST_PREFIX + "selectItem")
public class FrontSelectItemController {


    @Autowired
    private IPandaBusinessLineService iPandaBusinessLineService;

    @Autowired
    private IPandaAppService iPandaAppService;

    @Autowired
    private IPandaEnvService iPandaEnvService;


    @GetMapping("/businessLine")
    public ResultDto<SelectItemDto> businessLine() {
        return ResultDto.SUCCESS().setData(iPandaBusinessLineService.selectItem(false));
    }

    @GetMapping("/app")
    public StatusDto app(@RequestParam(value = "businessLineId") List<Long> businessLineIds) {
        PandaAppSo so = new PandaAppSo();
        so.getIns().add(InPair.builder().column("business_line_id").values(businessLineIds).build());
        return StatusDto.SUCCESS().setData(iPandaAppService.selectItem(false, so));
    }


    @GetMapping("/env")
    public StatusDto env() {
        return StatusDto.SUCCESS().setData(iPandaEnvService.selectItem(false));
    }

}
