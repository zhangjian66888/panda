package com.panda.core.service;

import com.panda.common.dto.SelectItemDto;
import com.panda.core.dto.PandaBusinessLineDto;
import com.panda.core.dto.search.PandaBusinessLineSo;
import com.panda.core.entity.PandaBusinessLine;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-21
 */
public interface IPandaBusinessLineService
        extends IBaseService<PandaBusinessLine, PandaBusinessLineDto, PandaBusinessLineSo> {

    List<SelectItemDto> selectItem(boolean all);

}
