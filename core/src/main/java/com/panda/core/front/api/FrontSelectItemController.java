package com.panda.core.front.api;

import com.panda.common.dto.ResultDto;
import com.panda.common.dto.SelectItemDto;
import com.panda.common.dto.StatusDto;
import com.panda.common.mybatis.InPair;
import com.panda.common.util.SelectItemUtil;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.search.PandaAppSo;
import com.panda.core.service.IPandaAppService;
import com.panda.core.service.IPandaBusinessLineService;
import com.panda.core.service.IPandaEnvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * com.panda.core.front.PandaAppController
 * <p>
 * DATE 2019/8/1
 *
 * @author zhanglijian.
 */
@RestController
@RequestMapping(CoreConst.FRONT_REQUEST_PREFIX + "selectItem")
public class FrontSelectItemController {


    @Autowired
    private IPandaBusinessLineService iPandaBusinessLineService;

    @Autowired
    private IPandaAppService iPandaAppService;

    @Autowired
    private IPandaEnvService iPandaEnvService;



    @GetMapping("/static")
    public StatusDto staticSelectItems(
            @RequestParam("type") String type,
            @RequestParam(value = "all", required = false, defaultValue = "false") Boolean all) {

        return StatusDto.SUCCESS().setData(SelectItemUtil.selectItem(type, all));
    }

    @GetMapping("/businessLine")
    public ResultDto<SelectItemDto> businessLine() {
        return ResultDto.SUCCESS().setData(iPandaBusinessLineService.selectItem(false));
    }

    @GetMapping("/app")
    public ResultDto app(@RequestParam(value = "businessLineId") String businessLineId) {
        PandaAppSo so = new PandaAppSo();
        List<Long> businessIds = Arrays.stream(businessLineId.split(",")).map(t->Long.valueOf(t))
                .collect(Collectors.toList());
        if (Objects.nonNull(businessIds) && !businessIds.isEmpty()){
            so.getIns().add(InPair.builder().column("business_line_id").values(businessIds).build());
        }
        return ResultDto.SUCCESS().setData(iPandaAppService.selectItem(false, so));
    }


    @GetMapping("/env")
    public ResultDto env() {
        return ResultDto.SUCCESS().setData(iPandaEnvService.selectItem(false));
    }

}
