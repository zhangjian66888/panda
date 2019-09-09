package com.panda.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.panda.common.enums.AppOwnerType;
import com.panda.common.enums.CodeType;
import com.panda.common.enums.DelState;
import com.panda.common.exception.PandaException;
import com.panda.common.util.BeanUtil;
import com.panda.common.util.TokenUtil;
import com.panda.core.dto.PandaAppDto;
import com.panda.core.dto.PandaAppOwnerDto;
import com.panda.core.dto.PandaAppSecretDto;
import com.panda.core.dto.PandaEnvDto;
import com.panda.core.dto.search.PandaAppSo;
import com.panda.core.entity.PandaApp;
import com.panda.core.entity.PandaAppOwner;
import com.panda.core.entity.PandaAppSecret;
import com.panda.core.mapper.PandaAppMapper;
import com.panda.core.mapper.PandaAppOwnerMapper;
import com.panda.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
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

    @Autowired
    private IPandaAppOwnerService iPandaAppOwnerService;

    @Autowired
    private PandaAppOwnerMapper pandaAppOwnerMapper;

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
            token.setSecret(TokenUtil.secret());
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
    public PandaAppSecretDto secret(Long code, String secret, String... profiles) {

        PandaAppSecret tokenQuery = new PandaAppSecret() {{
            setDelState(DelState.NO.getId());
            setAppCode(code);
            setSecret(secret);
        }};
        QueryWrapper<PandaAppSecret> queryWrapper = new QueryWrapper<>(tokenQuery);
        queryWrapper.in("env_profile", profiles);
        PandaAppSecret entity = iPandaAppSecretService.getOne(queryWrapper);
        return Optional.ofNullable(entity)
                .map(t -> BeanUtil.transBean(t, PandaAppSecretDto.class))
                .orElse(null);
    }

    @Override
    public String[] selectItemField() {
        return new String[]{"appCode", "appName"};
    }


    @Override
    public String flushSecret(PandaAppSecretDto dto) {
        dto.setSecret(TokenUtil.secret());
        iPandaAppSecretService.insertOrUpdate(dto);
        return dto.getSecret();
    }

    @Override
    public List<PandaAppOwnerDto> listOwners(Long code) {
        PandaAppOwnerDto query = PandaAppOwnerDto.builder().appCode(code).build();
        List<PandaAppOwnerDto> owners = iPandaAppOwnerService.find(query);
        return owners;
    }

    @Override
    public int saveOwner(PandaAppOwnerDto dto) {
        PandaAppOwner query = new PandaAppOwner();
        query.setAppCode(dto.getAppCode());
        query.setOwnerId(dto.getOwnerId());
        query.setDelState(DelState.NO.getId());
        PandaAppOwner pandaAppOwner = pandaAppOwnerMapper.selectOne(new QueryWrapper<>(query));
        if (Objects.nonNull(pandaAppOwner)) {
            throw new PandaException("请勿重复添加");
        }
        LocalDateTime now = LocalDateTime.now();
        pandaAppOwner = new PandaAppOwner();
        pandaAppOwner.setAppCode(dto.getAppCode());
        pandaAppOwner.setOwnerId(dto.getOwnerId());
        pandaAppOwner.setOwnerType(dto.getOwnerType());
        pandaAppOwner.setCreateTime(now);
        pandaAppOwner.setUpdateTime(now);
        return pandaAppOwnerMapper.insert(pandaAppOwner);
    }

    @Override
    public int deleteOwner(PandaAppOwnerDto dto) {
        PandaAppOwner query = new PandaAppOwner();
        query.setAppCode(dto.getAppCode());
        query.setOwnerId(dto.getOwnerId());
        query.setDelState(DelState.NO.getId());

        PandaAppOwner pandaAppOwner = pandaAppOwnerMapper.selectOne(new QueryWrapper<>(query));
        if (Objects.isNull(pandaAppOwner)) {
            return 1;
        }
        PandaAppOwner delete = new PandaAppOwner();
        delete.setId(pandaAppOwner.getId());
        delete.setDelState(DelState.YES.getId());
        delete.setUpdateTime(LocalDateTime.now());
        return pandaAppOwnerMapper.updateById(delete);
    }

    @Override
    public PandaAppOwnerDto findByAppCodeOwnerId(Long appCode, Long ownerId) {
        return findByAppCodeOwnerId(appCode, ownerId, null);
    }

    @Override
    public PandaAppOwnerDto findByAppCodeOwnerId(Long appCode, Long ownerId, AppOwnerType ownerType) {
        return iPandaAppOwnerService.findOne(PandaAppOwnerDto.builder().appCode(appCode).ownerType(ownerType.getId())
                .ownerId(ownerId).build());
    }

    @Override
    public List<PandaAppDto> listByCodes(List<Long> codes) {
        PandaApp query = new PandaApp();
        query.setDelState(DelState.NO.getId());
        QueryWrapper<PandaApp> queryWrapper = new QueryWrapper<>(query);
        queryWrapper.in("app_code", codes);
        List<PandaApp> list = list(queryWrapper);
        return Optional.ofNullable(list).orElse(Lists.newArrayList())
                .stream().map(t -> BeanUtil.transBean(t, PandaAppDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> findCodeByOwnerId(AppOwnerType ownerType, Long ownerId) {
        PandaAppOwner query = new PandaAppOwner();
        query.setDelState(DelState.NO.getId());
        query.setOwnerId(ownerId);
        query.setOwnerType(ownerType.getId());
        QueryWrapper<PandaAppOwner> queryWrapper = new QueryWrapper<>(query);
        queryWrapper.select("app_code");
        queryWrapper.nonEmptyOfEntity();
        List<PandaAppOwner> list = pandaAppOwnerMapper.selectList(queryWrapper);
        return Optional.ofNullable(list).orElse(Lists.newArrayList()).stream()
                .map(t -> t.getAppCode()).collect(Collectors.toList());
    }
}
