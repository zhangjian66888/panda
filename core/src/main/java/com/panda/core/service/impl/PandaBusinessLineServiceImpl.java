package com.panda.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.panda.common.dto.SelectItemDto;
import com.panda.common.enums.DelState;
import com.panda.core.dto.PandaBusinessLineDto;
import com.panda.core.dto.search.PandaBusinessLineSo;
import com.panda.core.entity.PandaBusinessLine;
import com.panda.core.mapper.PandaBusinessLineMapper;
import com.panda.core.service.IPandaBusinessLineService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<SelectItemDto> selectItem(boolean all) {
        PandaBusinessLine query = new PandaBusinessLine() {{
            setDelState(DelState.NO.getId());
        }};
        QueryWrapper<PandaBusinessLine> queryWrapper = new QueryWrapper<>(query);
        queryWrapper.select("id", "business_line_name");
        List<SelectItemDto> list = Optional.ofNullable(list(queryWrapper)).orElse(Lists.newArrayList())
                .stream()
                .map(t -> SelectItemDto.builder().id(t.getId()).value(t.getBusinessLineName()).build())
                .collect(Collectors.toList());
        if (all) {
            list.add(0, SelectItemDto.builder().value("全部").build());
        }
        return list;
    }
}
