package com.panda.core.mapper;

import com.panda.core.entity.PandaCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-24
 */
public interface PandaCodeMapper extends BaseMapper<PandaCode> {

    int increase (@Param("id")Long id, @Param("codeValue") Integer codeValue);

}
