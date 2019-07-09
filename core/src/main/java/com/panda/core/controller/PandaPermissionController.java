package com.panda.core.controller;


import com.panda.common.dto.SelectItemDto;
import com.panda.common.enums.MenuType;
import com.panda.common.enums.PermissionType;
import com.panda.common.util.SelectItemUtil;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.PandaPermissionDto;
import com.panda.core.dto.search.PandaPermissionSo;
import com.panda.core.entity.PandaPermission;
import com.panda.core.service.IPandaAppService;
import com.panda.core.service.IPandaBusinessLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-26
 */
@RestController
@RequestMapping(CoreConst.MAIN_REQUEST_PREFIX + "permission")
public class PandaPermissionController
        extends BaseController<PandaPermission, PandaPermissionDto, PandaPermissionSo>{

    @Autowired
    private IPandaBusinessLineService iPandaBusinessLineService;

    @Autowired
    private IPandaAppService iPandaAppService;

    @Override
    protected List<PandaPermissionDto> decorateList(List<PandaPermissionDto> list) {
        List<SelectItemDto> lines = iPandaBusinessLineService.selectItem(false);
        List<SelectItemDto> apps = iPandaAppService.selectItem(false);
        for (PandaPermissionDto dto : list) {
            dto.setBusinessLineName(SelectItemUtil.getValueById(dto.getBusinessLineId(), lines));
            dto.setAppName(SelectItemUtil.getValueById(dto.getAppCode(), apps));
            dto.setTypeShow(SelectItemUtil.getValueById(dto.getType(), PermissionType.class));
            dto.setMenuTypeShow(SelectItemUtil.getValueById(dto.getMenuType(), MenuType.class));
        }
        return super.decorateList(list);
    }


}
