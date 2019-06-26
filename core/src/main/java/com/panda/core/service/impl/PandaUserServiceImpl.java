package com.panda.core.service.impl;

import com.panda.core.dto.PandaUserDto;
import com.panda.core.dto.search.PandaUserSo;
import com.panda.core.entity.PandaUser;
import com.panda.core.mapper.PandaUserMapper;
import com.panda.core.service.IPandaUserService;
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
public class PandaUserServiceImpl
        extends BaseServiceImpl<PandaUserMapper, PandaUser, PandaUserDto, PandaUserSo>
        implements IPandaUserService {

}
