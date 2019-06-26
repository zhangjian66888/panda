package com.panda.core.service;

import com.panda.common.enums.CodeType;
import com.panda.core.dto.PandaCodeDto;
import com.panda.core.dto.search.PandaCodeSo;
import com.panda.core.entity.PandaCode;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-24
 */
public interface IPandaCodeService extends IBaseService<PandaCode, PandaCodeDto, PandaCodeSo> {


    int obtainCode(String type);

    int obtainCode(CodeType codeType);

}
