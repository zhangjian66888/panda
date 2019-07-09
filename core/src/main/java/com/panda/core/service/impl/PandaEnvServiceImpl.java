package com.panda.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.panda.common.enums.CodeType;
import com.panda.common.enums.DelState;
import com.panda.core.dto.PandaEnvDto;
import com.panda.core.dto.search.PandaEnvSo;
import com.panda.core.entity.PandaEnv;
import com.panda.core.mapper.PandaEnvMapper;
import com.panda.core.service.IPandaCodeService;
import com.panda.core.service.IPandaEnvService;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
public class PandaEnvServiceImpl extends BaseServiceImpl<PandaEnvMapper, PandaEnv, PandaEnvDto, PandaEnvSo>
        implements IPandaEnvService {

    @Autowired
    private IPandaCodeService iPandaCodeService;

    @Autowired
    private PandaEnvMapper pandaEnvMapper;

    @Override
    public boolean saveOrUpdate(PandaEnv entity) {
        if (Objects.nonNull(entity.getId())) {
            return updateById(entity);
        } else {
            long code = iPandaCodeService.obtainCode(CodeType.APP.getValue());
            entity.setEnvCode(code);
            save(entity);
            return true;
        }
    }

    @Override
    public String[] selectItemField() {
        return new String[]{"envCode", "envName"};
    }

    @Override
    public List<Long> profileToCode(String... profiles) {
        PandaEnv query = new PandaEnv();
        query.setDelState(DelState.NO.getId());
        QueryWrapper<PandaEnv> queryWrapper = new QueryWrapper<>(query);
        queryWrapper.select("env_code");
        queryWrapper.in("env_profile", profiles);
        List<PandaEnv> list = pandaEnvMapper.selectList(queryWrapper);
        return Optional.ofNullable(list).orElse(Lists.newArrayList())
                .stream().map(t -> t.getEnvCode())
                .collect(Collectors.toList());
    }
}
