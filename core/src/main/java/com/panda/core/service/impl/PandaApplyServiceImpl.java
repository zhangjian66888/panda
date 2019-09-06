package com.panda.core.service.impl;

import com.panda.core.dto.PandaApplyDto;
import com.panda.core.dto.search.PandaApplySo;
import com.panda.core.entity.PandaApply;
import com.panda.core.mapper.PandaApplyMapper;
import com.panda.core.service.IPandaApplyService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-09-05
 */
@Service
public class PandaApplyServiceImpl
        extends BaseServiceImpl<PandaApplyMapper, PandaApply, PandaApplyDto, PandaApplySo>
        implements IPandaApplyService {

}
