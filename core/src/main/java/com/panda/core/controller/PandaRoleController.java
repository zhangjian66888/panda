package com.panda.core.controller;


import com.panda.common.dto.SelectItemDto;
import com.panda.common.dto.StatusDto;
import com.panda.common.util.SelectItemUtil;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.PandaRoleDto;
import com.panda.core.dto.PandaRolePermissionDto;
import com.panda.core.dto.SelectedTagDto;
import com.panda.core.dto.search.PandaRoleSo;
import com.panda.core.entity.PandaRole;
import com.panda.core.service.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
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
@RequestMapping(CoreConst.MAIN_REQUEST_PREFIX + "role")
public class PandaRoleController extends BaseController<PandaRole, PandaRoleDto, PandaRoleSo> {

    @Autowired
    private IPandaBusinessLineService iPandaBusinessLineService;

    @Autowired
    private IPandaAppService iPandaAppService;

    @Autowired
    private IPandaEnvService iPandaEnvService;

    @Autowired
    private IPandaRoleService iPandaRoleService;

    @Override
    protected List<PandaRoleDto> decorateList(List<PandaRoleDto> list) {
        List<SelectItemDto> lines = iPandaBusinessLineService.selectItem(false);
        List<SelectItemDto> apps = iPandaAppService.selectItem(false);
        List<SelectItemDto> envs = iPandaEnvService.selectItem(false);
        for (PandaRoleDto dto : list) {
            dto.setBusinessLineName(SelectItemUtil.getValueById(dto.getBusinessLineId(), lines));
            dto.setAppName(SelectItemUtil.getValueById(dto.getAppCode(), apps));
            dto.setEnvName(SelectItemUtil.getValueById(dto.getEnvCode(), envs));
        }
        return super.decorateList(list);
    }

    @GetMapping("/permissions")
    public StatusDto permissions(@RequestParam(value = "id") Long id) {
        return StatusDto.SUCCESS().setData(iPandaRoleService.selectedByRoleId(id)
                .stream()
                .map(t -> SelectedTagDto.builder()
                        .id(t.getPermissionId())
                        .name(t.getPermissionShowName())
                        .title(t.getPermissionName())
                        .build())
                .collect(Collectors.toList()));
    }

    @PostMapping("/savePermission")
    public StatusDto savePermission(@Valid @RequestBody PandaRolePermissionDto dto) {
        iPandaRoleService.savePermission(dto);
        return StatusDto.SUCCESS();
    }

    @PostMapping("/deletePermission")
    @ResponseBody
    public StatusDto deletePermission(@Valid @RequestBody PandaRolePermissionDto dto) {
        iPandaRoleService.deletePermission(dto);
        return StatusDto.SUCCESS();
    }

}
