package com.panda.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.panda.common.enums.DelState;
import com.panda.core.dto.PandaAppSecretDto;
import com.panda.core.dto.search.PandaAppSecretSo;
import com.panda.core.entity.PandaAppSecret;
import com.panda.core.mapper.PandaAppSecretMapper;
import com.panda.core.service.IPandaAppSecretService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-21
 */
@Service
public class PandaAppSecretServiceImpl
        extends BaseServiceImpl<PandaAppSecretMapper, PandaAppSecret, PandaAppSecretDto, PandaAppSecretSo>
        implements IPandaAppSecretService {


    @Override
    public boolean saveOrUpdate(PandaAppSecret entity) {
        entity.setDelState(DelState.NO.getId());
        PandaAppSecret query = new PandaAppSecret();
        query.setDelState(DelState.NO.getId());
        query.setAppCode(entity.getAppCode());
        query.setEnvCode(entity.getEnvCode());
        PandaAppSecret appSecret = this.getOne(new QueryWrapper<>(query));
        if (Objects.nonNull(appSecret)) {
            entity.setId(appSecret.getId());
        }
        if (Objects.nonNull(entity.getId())) {
            return updateById(entity);
        } else {
            save(entity);
            return true;
        }
    }

}
