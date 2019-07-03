package com.panda.core.service.impl;

import com.google.common.collect.Lists;
import com.panda.common.enums.CodeType;
import com.panda.common.enums.DelState;
import com.panda.common.util.TokenUtil;
import com.panda.core.dto.PandaAppDto;
import com.panda.core.dto.PandaAppSecretDto;
import com.panda.core.dto.PandaEnvDto;
import com.panda.core.dto.search.PandaAppSo;
import com.panda.core.entity.PandaApp;
import com.panda.core.mapper.PandaAppMapper;
import com.panda.core.service.IPandaAppSecretService;
import com.panda.core.service.IPandaAppService;
import com.panda.core.service.IPandaCodeService;
import com.panda.core.service.IPandaEnvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 应用 服务实现类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-03
 */
@Service
public class PandaAppServiceImpl extends BaseServiceImpl<PandaAppMapper, PandaApp, PandaAppDto, PandaAppSo>
        implements IPandaAppService {

    @Autowired
    private IPandaCodeService iPandaCodeService;

    @Autowired
    private IPandaEnvService iPandaEnvService;

    @Autowired
    private IPandaAppSecretService iPandaAppSecretService;

    @Override
    public boolean saveOrUpdate(PandaApp entity) {
        if (Objects.nonNull(entity.getId())) {
            return updateById(entity);
        } else {
            long code = iPandaCodeService.obtainCode(CodeType.APP.getValue());
            entity.setAppCode(code);
            save(entity);
            generateToken(code);
            return true;
        }
    }

    public void generateToken(Long appCode) {
        List<PandaEnvDto> envs = iPandaEnvService.findAll();
        List<PandaAppSecretDto> tokens = Lists.newArrayList();
        for (PandaEnvDto env : envs) {
            PandaAppSecretDto token = new PandaAppSecretDto();
            token.setSecret(TokenUtil.token());
            token.setAppCode(appCode);
            token.setEnvCode(env.getEnvCode());
            token.setEnvProfile(env.getEnvProfile());
            tokens.add(token);
        }
        iPandaAppSecretService.insertBatch(tokens);
    }

    @Override
    public List<PandaAppSecretDto> secretByAppCode(Long code) {

        List<PandaEnvDto> envs = iPandaEnvService.findAll();
        Map<String, PandaAppSecretDto> map = envs.stream()
                .collect(Collectors.toMap(t -> t.getEnvProfile(),
                        t -> PandaAppSecretDto.builder()
                                .appCode(code)
                                .envCode(t.getEnvCode())
                                .envProfile(t.getEnvProfile())
                                .build()));
        PandaAppSecretDto tokenQuery = new PandaAppSecretDto() {{
            setDelState(DelState.NO.getId());
            setAppCode(code);
        }};
        List<PandaAppSecretDto> tokens = iPandaAppSecretService.find(tokenQuery);
        for (PandaAppSecretDto token : tokens) {
            Optional.ofNullable(map.get(token.getEnvProfile())).ifPresent(t ->
                    t.setSecret(token.getSecret()));
        }

        return new ArrayList<>(map.values());
    }

    @Override
    public String[] selectItemField() {
        return new String[]{"appCode", "appName"};
    }


    @Override
    public String flushSecret(PandaAppSecretDto dto) {
        dto.setSecret(TokenUtil.token());
        iPandaAppSecretService.insertOrUpdate(dto);
        return dto.getSecret();
    }
}
