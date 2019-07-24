package com.panda.client.cache;

import com.panda.api.dto.AuthAuthority;
import com.panda.api.dto.AuthUser;

import java.util.Map;
import java.util.Set;

/**
 * com.panda.client.cache.LocalCache
 * <p>
 * DATE 2019/7/18
 *
 * @author zhanglijian.
 */
public class AuthLocalCache {

    private Map<String, AuthUser> tokenUserMap;

    private Map<Long, AuthAuthority> authorityMap;

    private Map<Long, Set<Long>> roleAuthorityMap;

    public void load(){
    }
}
