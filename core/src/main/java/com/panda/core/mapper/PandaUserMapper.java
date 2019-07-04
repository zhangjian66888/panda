package com.panda.core.mapper;

import com.panda.core.entity.PandaUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-26
 */
public interface PandaUserMapper extends BaseMapper<PandaUser> {

    List<PandaUser> vagueLike(@Param("key") String key);

}
