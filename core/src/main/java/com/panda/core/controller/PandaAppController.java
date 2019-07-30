package com.panda.core.controller;

import com.google.common.collect.Lists;
import com.panda.common.dto.SelectItemDto;
import com.panda.common.dto.StatusDto;
import com.panda.common.util.SelectItemUtil;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.PandaAppDto;
import com.panda.core.dto.PandaAppOwnerDto;
import com.panda.core.dto.PandaAppSecretDto;
import com.panda.core.dto.PandaUserDto;
import com.panda.core.dto.search.PandaAppSo;
import com.panda.core.entity.PandaApp;
import com.panda.core.service.IPandaAppService;
import com.panda.core.service.IPandaBusinessLineService;
import com.panda.core.service.IPandaUserService;
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
 * com.panda.core.controller.PandaAppController
 * <p>
 * DATE 2019/6/4
 *
 * @author zhanglijian.
 */
@RestController
@RequestMapping(CoreConst.MAIN_REQUEST_PREFIX + "app")
public class PandaAppController extends BaseController<PandaApp, PandaAppDto, PandaAppSo> {

    @Autowired
    private IPandaAppService iPandaAppService;

    @Autowired
    private IPandaBusinessLineService iPandaBusinessLineService;

    @Autowired
    private IPandaUserService iPandaUserService;

    @Override
    protected List<PandaAppDto> decorateList(List<PandaAppDto> list) {
        List<SelectItemDto> lines = iPandaBusinessLineService.selectItem(false);
        for (PandaAppDto dto : list) {
            dto.setBusinessLineName(SelectItemUtil.getValueById(dto.getBusinessLineId(), lines));
        }
        return super.decorateList(list);
    }

    @GetMapping("/secrets")
    @ResponseBody
    public StatusDto secret(@RequestParam("appCode") Long appCode) {
        return StatusDto.SUCCESS().setData(iPandaAppService.secretByAppCode(appCode));
    }

    @PostMapping("/flushSecret")
    @ResponseBody
    public StatusDto flushSecret(@Valid @RequestBody PandaAppSecretDto dto) {
        return StatusDto.SUCCESS().setData(iPandaAppService.flushSecret(dto));
    }

    @GetMapping("/listOwners")
    public StatusDto listOwners(@RequestParam("appCode") Long appCode) {
        List<PandaAppOwnerDto> list = iPandaAppService.listOwners(appCode);
        if (Objects.isNull(list) || list.isEmpty()) {
            return StatusDto.SUCCESS();
        }
        List<Long> ids = list.stream().map(t -> t.getOwnerId()).collect(Collectors.toList());
        Map<Long, PandaUserDto> map = Optional.ofNullable(iPandaUserService.findListByIds(ids))
                .orElse(Lists.newArrayList())
                .stream().collect(Collectors.toMap(t -> t.getId(), t -> t));
        List<PandaAppOwnerDto> result = list.stream().map(t -> {
            t.setOwnerName(Optional.ofNullable(map.get(t.getOwnerId())).map(o -> o.getZhName()).orElse(null));
            return t;
        }).filter(t -> StringUtils.hasLength(t.getOwnerName())).collect(Collectors.toList());
        return StatusDto.SUCCESS().setData(result);
    }

    @PostMapping("/saveOwner")
    public StatusDto saveOwner(@Valid @RequestBody PandaAppOwnerDto dto) {
        iPandaAppService.saveOwner(dto);
        return StatusDto.SUCCESS();
    }

    @PostMapping("/deleteOwner")
    @ResponseBody
    public StatusDto deleteOwner(@Valid @RequestBody PandaAppOwnerDto dto) {
        iPandaAppService.deleteOwner(dto);
        return StatusDto.SUCCESS();
    }
}
