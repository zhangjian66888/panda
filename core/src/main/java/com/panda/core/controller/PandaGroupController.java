package com.panda.core.controller;


import com.panda.common.dto.SelectItemDto;
import com.panda.common.util.SelectItemUtil;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.PandaAppDto;
import com.panda.core.dto.PandaGroupDto;
import com.panda.core.dto.search.PandaGroupSo;
import com.panda.core.entity.PandaGroup;
import com.panda.core.service.IPandaBusinessLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-26
 */
@RestController
@RequestMapping(CoreConst.MAIN_REQUEST_PREFIX + "group")
public class PandaGroupController extends BaseController<PandaGroup, PandaGroupDto, PandaGroupSo> {


    @Autowired
    private IPandaBusinessLineService iPandaBusinessLineService;

    @Override
    protected List<PandaGroupDto> decorateList(List<PandaGroupDto> list) {
        List<SelectItemDto> lines = iPandaBusinessLineService.selectItem(false);
        for (PandaGroupDto dto : list) {
            dto.setBusinessLineName(SelectItemUtil.getValueById(dto.getBusinessLineId(), lines));
        }
        return super.decorateList(list);
    }
}
