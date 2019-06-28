package com.panda.core.service;

import com.panda.core.dto.PandaBusinessLineDto;
import com.panda.core.dto.search.PandaBusinessLineSo;
import com.panda.core.entity.PandaBusinessLine;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-21
 */
public interface IPandaBusinessLineService
        extends IBaseService<PandaBusinessLine, PandaBusinessLineDto, PandaBusinessLineSo>,
        ISelectItem<PandaBusinessLineSo> {
}
