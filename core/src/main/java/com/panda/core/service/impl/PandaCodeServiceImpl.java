package com.panda.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.panda.common.consts.CommonConst;
import com.panda.common.enums.CodeType;
import com.panda.common.enums.DelState;
import com.panda.common.exception.PandaException;
import com.panda.core.dto.PandaCodeDto;
import com.panda.core.dto.search.PandaCodeSo;
import com.panda.core.entity.PandaCode;
import com.panda.core.mapper.PandaCodeMapper;
import com.panda.core.service.IPandaCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-24
 */
@Service
public class PandaCodeServiceImpl
        extends BaseServiceImpl<PandaCodeMapper, PandaCode, PandaCodeDto, PandaCodeSo>
        implements IPandaCodeService {

    @Autowired
    private PandaCodeMapper pandaCodeMapper;

    @Override
    public int obtainCode(CodeType codeType) {
        return obtainCode(codeType.getValue());
    }

    @Override
    public int obtainCode(String codeType) {
        LocalDateTime now = LocalDateTime.now();
        PandaCode search = new PandaCode();
        search.setCodeType(codeType);
        search.setDelState(DelState.NO.getId());
        QueryWrapper<PandaCode> query = new QueryWrapper<>();
        PandaCode obtainCode = pandaCodeMapper.selectOne(query);
        int code = CommonConst.CODE_INITIAL_VALUE;
        if (Objects.nonNull(obtainCode)) {
            code = obtainCode.getCodeValue();
            int count = pandaCodeMapper.increase(obtainCode.getId(), code);
            if (count < 1) {
                throw new PandaException(String.format("obtain code conflict: %s", codeType));
            }
            code = code + 1;
        } else {
            PandaCode entity = new PandaCode();
            entity.setCodeType(codeType);
            entity.setCodeValue(code);
            entity.setUpdateTime(now);
            entity.setCreateTime(now);
            pandaCodeMapper.insert(entity);
        }
        return code;
    }
}
