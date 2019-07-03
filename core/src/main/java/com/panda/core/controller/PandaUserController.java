package com.panda.core.controller;


import com.panda.common.dto.SelectItemDto;
import com.panda.common.dto.StatusDto;
import com.panda.common.enums.UserType;
import com.panda.common.util.SelectItemUtil;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.PandaGroupDto;
import com.panda.core.dto.PandaUserDto;
import com.panda.core.dto.PandaUserRoleDto;
import com.panda.core.dto.SelectedTagDto;
import com.panda.core.dto.search.PandaUserSo;
import com.panda.core.entity.PandaUser;
import com.panda.core.service.IPandaBusinessLineService;
import com.panda.core.service.IPandaUserService;
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
@RequestMapping(CoreConst.MAIN_REQUEST_PREFIX + "user")
public class PandaUserController extends BaseController<PandaUser, PandaUserDto, PandaUserSo> {

    @Autowired
    private IPandaUserService iPandaUserService;

    @Autowired
    private IPandaBusinessLineService iPandaBusinessLineService;

    @Override
    protected List<PandaUserDto> decorateList(List<PandaUserDto> list) {
        List<SelectItemDto> lines = iPandaBusinessLineService.selectItem(false);
        for (PandaUserDto dto : list) {
            dto.setBusinessLineName(SelectItemUtil.getValueById(dto.getBusinessLineId(), lines));
            dto.setUserTypeShow(SelectItemUtil.getValueById(dto.getUserType(), UserType.class));
            dto.setPassword(null);
        }
        return super.decorateList(list);
    }

    @GetMapping("/roles")
    public StatusDto roles(@RequestParam(value = "id") Long id) {
        return StatusDto.SUCCESS().setData(iPandaUserService.selectedByUserId(id)
                .stream()
                .map(t -> SelectedTagDto.builder()
                        .id(t.getRoleId())
                        .name(t.getRoleName())
                        .build())
                .collect(Collectors.toList()));
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
