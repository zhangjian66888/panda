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
import com.panda.core.service.IPandaBusinessLineService;
import com.panda.core.service.IPandaGroupService;
import com.panda.core.service.IPandaRoleService;
import com.panda.core.service.IPandaUserService;
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
        Map<Long, PandaRoleDto> map = Optional.ofNullable(iPandaRoleService.findListByIds(ids))
                .orElse(Lists.newArrayList())
                .stream().collect(Collectors.toMap(t -> t.getId(), t -> t));
        List<SelectedTagDto> tags = list.stream().map(t -> {
            SelectedTagDto tagDto = new SelectedTagDto();
            tagDto.setValue(t.getRoleId());
            Optional.ofNullable(map.get(t.getRoleId())).ifPresent(st -> tagDto.setLabel(st.getRoleName()));
            return tagDto;
        }).collect(Collectors.toList());
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
