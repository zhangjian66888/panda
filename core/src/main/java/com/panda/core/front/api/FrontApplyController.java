package com.panda.core.front.api;

import com.panda.common.dto.PageDto;
import com.panda.common.dto.ResultDto;
import com.panda.common.enums.ApplyState;
import com.panda.common.enums.ApplyType;
import com.panda.common.util.SelectItemUtil;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.PandaApplyDto;
import com.panda.core.dto.search.PandaApplySo;
import com.panda.core.front.dto.search.FrontApplySo;
import com.panda.core.security.SecurityUser;
import com.panda.core.security.SecurityUserContext;
import com.panda.core.service.IPandaApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * com.panda.core.front.api.FrontApplyController
 * <p>
 * DATE 2019/9/6
 *
 * @author zhanglijian.
 */

@RestController
@RequestMapping(CoreConst.FRONT_REQUEST_PREFIX + "apply")
public class FrontApplyController {

    @Autowired
    private IPandaApplyService iPandaApplyService;

    @PostMapping("search")
    public Mono<ResultDto<PageDto<PandaApplyDto>>> search(@RequestBody FrontApplySo search) {
        return Mono.fromSupplier(() -> {
            SecurityUser uer = SecurityUserContext.getContext();
            PandaApplySo so = new PandaApplySo(){{
                setApplicantId(uer.getUserId());
                in("apply_type", search.getApplyTypes());
                in("apply_state", search.getApplyState());
            }};

            PageDto<PandaApplyDto> pageDto = iPandaApplyService.search(so);
            if (Objects.nonNull(pageDto)
                    && Objects.nonNull(pageDto.getRecords()) && !pageDto.getRecords().isEmpty()) {
                for (PandaApplyDto dto : pageDto.getRecords()) {
                    dto.setApplyTypeLabel(SelectItemUtil.getValueById(dto.getApplyType(), ApplyType.class));
                    dto.setApplyStateLabel(SelectItemUtil.getValueById(dto.getApplyState(), ApplyState.class));
                }
            }
            return ResultDto.SUCCESS().setData(pageDto);
        });
    }
}
