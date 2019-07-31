package com.panda.core.controller;


import com.google.common.collect.Lists;
import com.panda.common.dto.SelectItemDto;
import com.panda.common.dto.StatusDto;
import com.panda.common.enums.UserType;
import com.panda.common.mybatis.InPair;
import com.panda.common.util.SelectItemUtil;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.*;
import com.panda.core.dto.search.PandaBusinessLineSo;
import com.panda.core.dto.search.PandaUserSo;
import com.panda.core.entity.PandaUser;
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
@RequestMapping(CoreConst.MAIN_REQUEST_PREFIX + "user")
public class PandaUserController extends BaseController<PandaUser, PandaUserDto, PandaUserSo> {

    @Autowired
    private IPandaUserService iPandaUserService;

    @Autowired
    private IPandaBusinessLineService iPandaBusinessLineService;

    @Autowired
    private IPandaGroupService iPandaGroupService;

    @Autowired
    private IPandaRoleService iPandaRoleService;

    @Autowired
    private IPandaAppService iPandaAppService;

    @GetMapping("vague")
    public StatusDto vagueFullQuery(@RequestParam("key") String key) {
        return StatusDto.SUCCESS().setData(iPandaUserService.vagueFullQuery(key));
    }

    @Override
    protected List<PandaUserDto> decorateList(List<PandaUserDto> list) {
        List<Long> businessLineIds = Lists.newArrayList();
        List<Long> groupIds = Lists.newArrayList();
        for (PandaUserDto dto : list) {
            businessLineIds.add(dto.getBusinessLineId());
            groupIds.add(dto.getGroupId());
        }
        List<SelectItemDto> lines = iPandaBusinessLineService.selectItem(false, new PandaBusinessLineSo() {{
            setIns(Lists.newArrayList(InPair.builder().column("id").values(businessLineIds).build()));
        }});
        List<SelectItemDto> groups = iPandaGroupService.selectItem(false, groupIds);
        for (PandaUserDto dto : list) {
            dto.setBusinessLineName(SelectItemUtil.getValueById(dto.getBusinessLineId(), lines));
            dto.setGroupName(SelectItemUtil.getValueById(dto.getGroupId(), groups));
            dto.setUserTypeShow(SelectItemUtil.getValueById(dto.getUserType(), UserType.class));
            dto.setPassword(null);
        }
        return super.decorateList(list);
    }

    @Override
    protected PandaUserDto decorateDto(PandaUserDto dto) {
        dto.setPassword(null);
        return super.decorateDto(dto);
    }

    @GetMapping("/roles")
    public StatusDto roles(@RequestParam(value = "id") Long id) {
        List<PandaUserRoleDto> list = iPandaUserService.rolesByUserId(id);
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
    public StatusDto saveRole(@Valid @RequestBody PandaUserRoleDto dto) {
        iPandaUserService.saveRole(dto);
        return StatusDto.SUCCESS();
    }

    @PostMapping("/deleteRole")
    @ResponseBody
    public StatusDto deleteRole(@Valid @RequestBody PandaUserRoleDto dto) {
        iPandaUserService.deleteRole(dto);
        return StatusDto.SUCCESS();
    }


}
