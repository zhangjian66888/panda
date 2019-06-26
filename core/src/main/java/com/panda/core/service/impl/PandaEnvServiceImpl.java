package com.panda.core.service.impl;

import com.panda.common.enums.CodeType;
import com.panda.core.dto.PandaEnvDto;
import com.panda.core.dto.search.PandaEnvSo;
import com.panda.core.entity.PandaApp;
import com.panda.core.entity.PandaEnv;
import com.panda.core.mapper.PandaEnvMapper;
import com.panda.core.service.IPandaCodeService;
import com.panda.core.service.IPandaEnvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
public class PandaEnvServiceImpl extends BaseServiceImpl<PandaEnvMapper, PandaEnv, PandaEnvDto, PandaEnvSo>
        implements IPandaEnvService {

    @Autowired
    private IPandaCodeService iPandaCodeService;

    @Override
    public boolean saveOrUpdate(PandaEnv entity) {
        if (Objects.nonNull(entity.getId())) {
            return updateById(entity);
        } else {
            int code = iPandaCodeService.obtainCode(CodeType.APP.getValue());
            entity.setEnvCode(code);
            save(entity);
            return true;
        }
    }

}
