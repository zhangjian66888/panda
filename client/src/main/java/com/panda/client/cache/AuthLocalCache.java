package com.panda.client.cache;

import com.panda.api.dto.AuthResource;
import com.panda.client.clients.AuthClient;
import com.panda.client.config.AuthProperties;
import com.panda.common.dto.ResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * com.panda.client.cache.LocalCache
 * <p>
 * DATE 2019/7/18
 *
 * @author zhanglijian.
 */
@Component
@Slf4j
public class AuthLocalCache implements ApplicationRunner {

    private Map<Long, AuthResource> resourceMap;

    private Map<Long, Set<Long>> roleResourceMap;

    @Autowired
    private AuthClient authClient;

    @Autowired
    private AuthProperties authProperties;


    public void load() {
        Map<Long, AuthResource> tmpResourceMap = null;
        Map<Long, Set<Long>> tmpRoleResourceMap = null;

        ResultDto<List<AuthResource>> resourceResult = authClient.resources(authProperties.getAppCode(),
                authProperties.getAppSecret(), authProperties.profile());
        if (resourceResult.getCode() == ResultDto.CODE_SUCESS
                && Objects.nonNull(resourceResult.getData()) && !resourceResult.getData().isEmpty()) {
            tmpResourceMap = resourceResult.getData().stream().collect(Collectors.toMap(t -> t.getResourceId(), t -> t));
        }

        ResultDto<Map<Long, Set<Long>>> roleResult = authClient.roles(authProperties.getAppCode(),
                authProperties.getAppSecret(), authProperties.profile());

        if (roleResult.getCode() == ResultDto.CODE_SUCESS
                && Objects.nonNull(roleResult.getData()) && !roleResult.getData().isEmpty()) {
            tmpRoleResourceMap = roleResult.getData();
        }
        if (Objects.nonNull(tmpResourceMap) && Objects.nonNull(tmpRoleResourceMap)) {
            this.resourceMap = tmpResourceMap;
            this.roleResourceMap = tmpRoleResourceMap;
        }
    }

    public Set<Long> getIdsByRoleIds(Set<Long> ids) {
        return ids.stream().map(t -> roleResourceMap.get(t))
                .filter(t -> Objects.nonNull(t)).flatMap(t -> t.stream()).collect(Collectors.toSet());
    }

    public List<AuthResource> getResourceByIds(Set<Long> ids) {
        return ids.stream().map(t -> resourceMap.get(t)).filter(t -> Objects.nonNull(t)).collect(Collectors.toList());
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("panda resource load start");
        load();
        log.info("panda resource load end");
    }
}
