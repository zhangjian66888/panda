package com.panda.client.endpoint;

import com.panda.client.cache.AuthLocalCache;
import com.panda.client.consts.AuthConst;
import com.panda.common.dto.ResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.panda.client.ClientController
 * <p>
 * DATE 2019/7/25
 *
 * @author zhanglijian.
 */
@RestController
@RequestMapping(AuthConst.AUTH_REQUEST_PREFIX)
@Slf4j
public class LocalCacheController {

    @Autowired
    private AuthLocalCache authLocalCache;

    @RequestMapping(value = "/flush", method = RequestMethod.GET)
    public ResultDto<String> flush() {
        log.info("panda resource flush start");
        authLocalCache.load();
        log.info("panda resource flush end");
        return ResultDto.SUCCESS();
    }
}
