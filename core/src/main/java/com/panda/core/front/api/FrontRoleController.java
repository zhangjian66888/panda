package com.panda.core.front.api;

import com.panda.common.dto.PageDto;
import com.panda.common.dto.ResultDto;
import com.panda.common.dto.SelectItemDto;
import com.panda.common.util.BeanUtil;
import com.panda.common.util.SelectItemUtil;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.PandaRoleDto;
import com.panda.core.dto.search.PandaAppSo;
import com.panda.core.dto.search.PandaEnvSo;
import com.panda.core.dto.search.PandaRoleSo;
import com.panda.core.front.dto.search.FrontRoleSo;
import com.panda.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * com.panda.core.front.PandaRoleController
 * <p>
 * DATE 2019/8/1
 *
 * @author zhanglijian.
 */
@RestController
@RequestMapping(CoreConst.FRONT_REQUEST_PREFIX + "role")
public class FrontRoleController {

    @Autowired
    private IPandaRoleService iPandaRoleService;

    @Autowired
    private IPandaBusinessLineService iPandaBusinessLineService;

    @Autowired
    private IPandaAppService iPandaAppService;

    @Autowired
    private IPandaEnvService iPandaEnvService;


    @PostMapping("search")
    public ResultDto<PageDto<PandaRoleDto>> search(@RequestBody FrontRoleSo search) {
        PandaRoleSo so = BeanUtil.transBean(search, PandaRoleSo.class);
        so.in("env_code", search.getEnvCodes())
                .in("business_line_id", search.getBusinessLineIds())
                .in("app_code", search.getAppCodes());
        PageDto<PandaRoleDto> pageDto = iPandaRoleService.search(so);
        if (Objects.nonNull(pageDto)
                && Objects.nonNull(pageDto.getRecords()) && !pageDto.getRecords().isEmpty()) {
            List<Long> envCodes = pageDto.getRecords().stream().map(t -> t.getEnvCode()).collect(Collectors.toList());
            List<Long> blLds = pageDto.getRecords().stream().map(t -> t.getBusinessLineId()).collect(Collectors.toList());
            List<Long> appCodes = pageDto.getRecords().stream().map(t -> t.getAppCode()).collect(Collectors.toList());
            List<SelectItemDto> envs = iPandaEnvService.selectItem(false, new PandaEnvSo().in("env_code", envCodes));
            List<SelectItemDto> lines = iPandaBusinessLineService.selectItem(false, blLds);
            List<SelectItemDto> apps = iPandaAppService.selectItem(false, new PandaAppSo().in("app_code", appCodes));

            for (PandaRoleDto dto : pageDto.getRecords()) {
                dto.setBusinessLineName(SelectItemUtil.getValueById(dto.getBusinessLineId(), lines));
                dto.setAppName(SelectItemUtil.getValueById(dto.getAppCode(), apps));
                dto.setEnvName(SelectItemUtil.getValueById(dto.getEnvCode(), envs));
            }
        }
        return ResultDto.SUCCESS().setData(pageDto);
    }
}
