package com.panda.core.controller;


import com.panda.common.dto.StatusDto;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.PandaBusinessLineDto;
import com.panda.core.dto.search.PandaBusinessLineSo;
import com.panda.core.entity.PandaBusinessLine;
import com.panda.core.service.IPandaBusinessLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private IPandaBusinessLineService iPandaBusinessLineService;

    @GetMapping("/selectItem")
    public StatusDto dynamicSelectItem(
            @RequestParam(value = "all", required = false, defaultValue = "false") Boolean all) {
        return StatusDto.SUCCESS().setData(iPandaBusinessLineService.selectItem(all));
    }
}
