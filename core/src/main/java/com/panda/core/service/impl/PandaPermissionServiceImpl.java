package com.panda.core.service.impl;

import com.panda.core.dto.PandaPermissionDto;
import com.panda.core.dto.search.PandaPermissionSo;
import com.panda.core.entity.PandaPermission;
import com.panda.core.mapper.PandaPermissionMapper;
import com.panda.core.service.IPandaPermissionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-26
 */
@Service
public class PandaPermissionServiceImpl
        extends BaseServiceImpl<PandaPermissionMapper, PandaPermission, PandaPermissionDto, PandaPermissionSo>
        implements IPandaPermissionService {

    @Override
    public String[] findListByIdsField() {
        return new String[]{"id", "name", "show_name", "url", "method", "type", "action",
                "menu_type", "parent_id", "menu_icon","menu_sequence"};
    }
}
