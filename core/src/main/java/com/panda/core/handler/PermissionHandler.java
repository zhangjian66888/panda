package com.panda.core.handler;

import com.panda.core.dto.PandaPermissionDto;
import com.panda.core.service.IPandaPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * com.panda.core.handler.PermissionHandler
 * <p>
 * DATE 2019/7/24
 *
 * @author zhanglijian.
 */
@Component
public class PermissionHandler {

    @Autowired
    private IPandaPermissionService iPandaPermissionService;

    public List<PandaPermissionDto> resources(Long appCode) {
        return iPandaPermissionService.find(PandaPermissionDto.builder().appCode(appCode).build());
    }
}
