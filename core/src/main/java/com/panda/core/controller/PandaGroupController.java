package com.panda.core.controller;


import com.google.common.collect.Lists;
import com.panda.common.dto.SelectItemDto;
import com.panda.common.dto.StatusDto;
import com.panda.common.util.SelectItemUtil;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.*;
import com.panda.core.dto.search.PandaGroupSo;
import com.panda.core.entity.PandaGroup;
import com.panda.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
@RequestMapping(CoreConst.MAIN_REQUEST_PREFIX + "group")
public class PandaGroupController extends BaseController<PandaGroup, PandaGroupDto, PandaGroupSo> {

    @Autowired
    private IPandaGroupService iPandaGroupService;

    @Autowired
    private IPandaBusinessLineService iPandaBusinessLineService;


    @Autowired
    private IPandaRoleService iPandaRoleService;

    @Autowired
    private IPandaUserService iPandaUserService;

    @Autowired
    private IPandaAppService iPandaAppService;

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

        List<PandaGroupRoleDto> list = iPandaGroupService.rolesByGroupId(id);
        if (Objects.isNull(list) || list.isEmpty()) {
            return StatusDto.SUCCESS();
        }
        List<Long> ids = list.stream().map(t -> t.getRoleId()).collect(Collectors.toList());
        List<PandaRoleDto> roles = iPandaRoleService.findListByIds(ids);
        if (Objects.isNull(roles) || roles.isEmpty()) {
            return StatusDto.SUCCESS();
        }
        List<Long> appCodes = roles.stream().map(t -> t.getAppCode()).collect(Collectors.toList());
        List<PandaAppDto> apps = iPandaAppService.listByCodes(appCodes);
        if (Objects.isNull(apps) || apps.isEmpty()) {
            return StatusDto.SUCCESS();
        }

        Map<Long, PandaRoleDto> map = roles.stream().collect(Collectors.toMap(t -> t.getId(), t -> t));
        Map<Long, PandaAppDto> appMap = apps.stream().collect(Collectors.toMap(t -> t.getAppCode(), t -> t));

        List<SelectedTagDto> tags = list.stream().map(t -> {
            SelectedTagDto tagDto = new SelectedTagDto();
            tagDto.setValue(t.getRoleId());
            PandaRoleDto roleDto = map.get(t.getRoleId());
            if (Objects.isNull(roleDto)) {
                return tagDto;
            }
            String roleName = Optional.ofNullable(roleDto).map(r -> r.getRoleName()).orElse(String.valueOf(t.getRoleId()));
            String appName = Optional.ofNullable(appMap.get(roleDto.getAppCode())).map(a -> a.getAppName())
                    .orElse(String.valueOf(roleDto.getAppCode()));
            tagDto.setLabel(roleName.concat("(").concat(appName).concat(")"));
            return tagDto;
        }).filter(t -> StringUtils.hasLength(t.getLabel())).collect(Collectors.toList());
        return StatusDto.SUCCESS().setData(tags);
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

    @GetMapping("/users")
    public StatusDto users(@RequestParam(value = "id") Long id) {

        List<PandaGroupUserDto> list = iPandaGroupService.usersByGroupId(id);
        if (Objects.isNull(list) || list.isEmpty()) {
            return StatusDto.SUCCESS();
        }
        List<Long> ids = list.stream().map(t -> t.getUserId()).collect(Collectors.toList());
        Map<Long, PandaUserDto> map = Optional.ofNullable(iPandaUserService.findListByIds(ids))
                .orElse(Lists.newArrayList())
                .stream().collect(Collectors.toMap(t -> t.getId(), t -> t));
        List<SelectedTagDto> tags = list.stream().map(t -> {
            SelectedTagDto tagDto = new SelectedTagDto();
            tagDto.setValue(t.getUserId());
            Optional.ofNullable(map.get(t.getUserId()))
                    .ifPresent(st -> tagDto.setLabel(st.getZhName().concat("(").concat(st.getMobile()).concat(")")));
            return tagDto;
        }).collect(Collectors.toList());
        return StatusDto.SUCCESS().setData(tags);
    }

    @PostMapping("/saveUser")
    public StatusDto saveUser(@Valid @RequestBody PandaGroupUserDto dto) {
        iPandaGroupService.saveUser(dto);
        return StatusDto.SUCCESS();
    }

    @PostMapping("/deleteUser")
    @ResponseBody
    public StatusDto deleteUser(@Valid @RequestBody PandaGroupUserDto dto) {
        iPandaGroupService.deleteUser(dto);
        return StatusDto.SUCCESS();
    }

}
