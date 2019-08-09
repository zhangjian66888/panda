package com.panda.core.front.api;

import com.panda.common.dto.PageDto;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.PandaRoleDto;
import com.panda.core.dto.search.PandaRoleSo;
import com.panda.core.service.IPandaRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * com.panda.core.front.PandaRoleController
 * <p>
 * DATE 2019/8/1
 *
 * @author zhanglijian.
 */
@RestController
@RequestMapping(CoreConst.FRONT_REQUEST_PREFIX + "role")
public class FrontRoleController {

    @Autowired
    private IPandaRoleService iPandaRoleService;

    @RequestMapping("search")
    public PageDto<PandaRoleDto> search(PandaRoleSo search) {
        PageDto pageDto = iPandaRoleService.search(search);
        if (Objects.nonNull(pageDto)
                && Objects.nonNull(pageDto.getRecords()) && !pageDto.getRecords().isEmpty()) {

        }
        return pageDto;
    }
}
