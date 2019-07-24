package com.panda.client.clients;

import com.panda.api.open.AuthApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * com.panda.client.fegin.AuthClient
 * <p>
 * DATE 2019/7/24
 *
 * @author zhanglijian.
 */
@FeignClient("accountX")
public interface AuthClient extends AuthApi {
}
