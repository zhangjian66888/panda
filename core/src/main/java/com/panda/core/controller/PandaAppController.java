package com.panda.core.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.panda.common.dto.SelectItemDto;
import com.panda.common.dto.StatusDto;
import com.panda.common.enums.DelState;
import com.panda.common.util.SelectItemUtil;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.PandaAppDto;
import com.panda.core.dto.search.PandaAppSo;
import com.panda.core.entity.PandaApp;
import com.panda.core.entity.PandaBusinessLine;
import com.panda.core.service.IPandaAppService;
import com.panda.core.service.IPandaBusinessLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * com.panda.core.controller.PandaAppController
 * <p>
 * DATE 2019/6/4
 *
 * @author zhanglijian.
 */
@RestController
@RequestMapping(CoreConst.MAIN_REQUEST_PREFIX + "app")
public class PandaAppController extends BaseController<PandaApp, PandaAppDto, PandaAppSo> {

    @Autowired
    private IPandaAppService iPandaAppService;

    @Autowired
    private IPandaBusinessLineService pandaBusinessLineService;

    @Override
    protected List<PandaAppDto> decorateList(List<PandaAppDto> list) {
        List<SelectItemDto> lines = pandaBusinessLineService.selectItem(false);
        for (PandaAppDto dto : list) {
            dto.setBusinessLineName(SelectItemUtil.getValueById(dto.getBusinessLineId(), lines));
        }
        return super.decorateList(list);
    }

    @GetMapping("/token")
    @ResponseBody
    public StatusDto token(@RequestParam("appCode") Integer appCode) {
        return StatusDto.SUCCESS().setData(iPandaAppService.tokenByAppCode(appCode));
    }
}
