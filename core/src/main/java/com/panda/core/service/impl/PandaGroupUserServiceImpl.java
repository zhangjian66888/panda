package com.panda.core.service.impl;

import com.panda.core.dto.PandaGroupUserDto;
import com.panda.core.dto.search.PandaGroupUserSo;
import com.panda.core.entity.PandaGroupUser;
import com.panda.core.mapper.PandaGroupUserMapper;
import com.panda.core.service.IPandaGroupUserService;
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
public class PandaGroupUserServiceImpl
        extends BaseServiceImpl<PandaGroupUserMapper, PandaGroupUser, PandaGroupUserDto, PandaGroupUserSo>
        implements IPandaGroupUserService {

}
