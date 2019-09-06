package com.panda.core.handler;

import com.panda.core.service.IPandaApplyRoleService;
import com.panda.core.service.IPandaApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * com.panda.core.handler.ApplyHandler
 * <p>
 * DATE 2019/9/6
 *
 * @author zhanglijian.
 */
@Component
public class ApplyHandler {

    @Autowired
    private IPandaApplyService iPandaApplyService;

    @Autowired
    private IPandaApplyRoleService iPandaApplyRoleService;

    public void deleteApply(Long id) {
        iPandaApplyService.deleteById(id);
        iPandaApplyRoleService.deleteByApplyId(id);
    }

    public void cancelApply(Long id) {
        iPandaApplyService.cancelById(id);
        iPandaApplyRoleService.cancelByApplyId(id);
    }

}
