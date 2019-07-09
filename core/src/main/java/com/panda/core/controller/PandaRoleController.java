package com.panda.core.controller;


import com.google.common.collect.Lists;
import com.panda.common.dto.SelectItemDto;
import com.panda.common.dto.StatusDto;
import com.panda.common.util.SelectItemUtil;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.PandaPermissionDto;
import com.panda.core.dto.PandaRoleDto;
import com.panda.core.dto.PandaRolePermissionDto;
import com.panda.core.dto.SelectedTagDto;
import com.panda.core.dto.search.PandaRoleSo;
import com.panda.core.entity.PandaRole;
import com.panda.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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

    @Autowired
    private IPandaPermissionService iPandaPermissionService;

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

        List<PandaRolePermissionDto> list = iPandaRoleService.permissionsByRoleId(id);
        if (Objects.isNull(list) || list.isEmpty()) {
            return StatusDto.SUCCESS();
        }
        List<Long> ids = list.stream().map(t -> t.getPermissionId()).collect(Collectors.toList());
        Map<Long, PandaPermissionDto> map = Optional.ofNullable(iPandaPermissionService.findListByIds(ids))
                .orElse(Lists.newArrayList())
                .stream().collect(Collectors.toMap(t -> t.getId(), t -> t));
        List<SelectedTagDto> tags = list.stream().map(t -> {
            SelectedTagDto tagDto = new SelectedTagDto();
            tagDto.setValue(t.getPermissionId());
            Optional.ofNullable(map.get(t.getPermissionId())).ifPresent(st -> {
                tagDto.setLabel(st.getShowName());
                tagDto.setTitle(st.getName());
            });
            return tagDto;
        }).collect(Collectors.toList());
        return StatusDto.SUCCESS().setData(tags);
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
