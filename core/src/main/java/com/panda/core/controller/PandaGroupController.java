package com.panda.core.controller;


import com.panda.common.dto.SelectItemDto;
import com.panda.common.dto.StatusDto;
import com.panda.common.util.SelectItemUtil;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.*;
import com.panda.core.dto.search.PandaGroupSo;
import com.panda.core.entity.PandaGroup;
import com.panda.core.service.IPandaBusinessLineService;
import com.panda.core.service.IPandaGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    private IPandaGroupService iPandaGroupService;

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

    @GetMapping("/roles")
    public StatusDto roles(@RequestParam(value = "id") Long id) {
        return StatusDto.SUCCESS().setData(iPandaGroupService.selectedByGroupId(id)
                .stream()
                .map(t -> SelectedTagDto.builder()
                        .id(t.getRoleId())
                        .name(t.getRoleName())
                        .build())
                .collect(Collectors.toList()));
    }

    @PostMapping("/saveRole")
    public StatusDto saveRole(@Valid @RequestBody PandaGroupRoleDto dto) {
        iPandaGroupService.saveRole(dto);
        return StatusDto.SUCCESS();
    }

    @PostMapping("/deleteRole")
    @ResponseBody
    public StatusDto deleteRole(@Valid @RequestBody PandaGroupRoleDto dto) {
        iPandaGroupService.deleteRole(dto);
        return StatusDto.SUCCESS();
    }


}
