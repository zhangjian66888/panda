package com.panda.core.controller;

import com.panda.common.dto.StatusDto;
import com.panda.common.util.SelectItemUtil;
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

/**
 * com.panda.core.controller.SelectItemControler
 * <p>
 * DATE 2019/6/24
 *
 * @author zhanglijian.
 */
@RestController
@RequestMapping(CoreConst.MAIN_REQUEST_PREFIX + "selectItem")
public class SelectItemController {

    @Autowired
    private IPandaBusinessLineService iPandaBusinessLineService;

    @Autowired
    private IPandaAppService iPandaAppService;

    @Autowired
    private IPandaEnvService iPandaEnvService;

    @GetMapping("/static")
    public StatusDto staticSelectItems(
            @RequestParam("type") String type,
            @RequestParam(value = "all", required = false, defaultValue = "false") Boolean all) {

        return StatusDto.SUCCESS().setData(SelectItemUtil.selectItem(type, all));
    }

    @GetMapping("/businessLine")
    public StatusDto businessLine(
            @RequestParam(value = "all", required = false, defaultValue = "false") Boolean all) {
        return StatusDto.SUCCESS().setData(iPandaBusinessLineService.selectItem(all));
    }

    @GetMapping("/app")
    public StatusDto app(
            @RequestParam(value = "businessLineId", required = false, defaultValue = "0") Long businessLineId,
            @RequestParam(value = "all", required = false, defaultValue = "false") Boolean all) {
        PandaAppSo so = new PandaAppSo();
        if (businessLineId > 0) {
            so.setBusinessLineId(businessLineId);
        }
        return StatusDto.SUCCESS().setData(iPandaAppService.selectItem(all, so));
    }

    @GetMapping("/env")
    public StatusDto env(
            @RequestParam(value = "all", required = false, defaultValue = "false") Boolean all) {
        return StatusDto.SUCCESS().setData(iPandaEnvService.selectItem(all));
    }

}
