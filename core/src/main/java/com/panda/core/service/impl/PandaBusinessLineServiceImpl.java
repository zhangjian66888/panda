package com.panda.core.service.impl;

import com.panda.core.dto.PandaBusinessLineDto;
import com.panda.core.dto.search.PandaBusinessLineSo;
import com.panda.core.entity.PandaBusinessLine;
import com.panda.core.mapper.PandaBusinessLineMapper;
import com.panda.core.service.IPandaBusinessLineService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-21
 */
@Service
public class PandaBusinessLineServiceImpl
        extends BaseServiceImpl<PandaBusinessLineMapper, PandaBusinessLine, PandaBusinessLineDto, PandaBusinessLineSo>
        implements IPandaBusinessLineService {

    @Override
    public String[] selectItemField() {
        return new String[]{"id", "businessLineName"};
    }
}
