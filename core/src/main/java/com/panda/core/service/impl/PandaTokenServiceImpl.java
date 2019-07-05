package com.panda.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.panda.common.enums.DelState;
import com.panda.common.util.BeanUtil;
import com.panda.core.dto.PandaTokenDto;
import com.panda.core.dto.search.PandaTokenSo;
import com.panda.core.entity.PandaToken;
import com.panda.core.mapper.PandaTokenMapper;
import com.panda.core.service.IPandaTokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-07-04
 */
@Service
public class PandaTokenServiceImpl extends BaseServiceImpl<PandaTokenMapper, PandaToken, PandaTokenDto, PandaTokenSo>
        implements IPandaTokenService {

    @Autowired
    private PandaTokenMapper pandaTokenMapper;

    @Override
    public PandaTokenDto validToken(Long userId) {
        PandaToken query = new PandaToken();
        query.setUserId(userId);
        query.setDelState(DelState.NO.getId());
        QueryWrapper<PandaToken> queryWrapper = new QueryWrapper<>(query);
        queryWrapper.ge("expire_time", LocalDateTime.now());
        return Optional.ofNullable(pandaTokenMapper.selectOne(queryWrapper))
                .map(t -> BeanUtil.transBean(t, PandaTokenDto.class))
                .orElse(null);
    }
}
