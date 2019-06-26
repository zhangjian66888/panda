package com.panda.common.dto;

import com.panda.common.consts.CommonConst;
import lombok.Getter;
import lombok.Setter;

/**
 * com.panda.common.dto.BaseSearchDto
 * <p>
 * DATE 2019/6/3
 *
 * @author zhanglijian.
 */
@Getter
@Setter
public abstract class BaseSo {

    protected Long id;
    private Integer delState;

    protected String sortField;
    protected String sortOrder;

    protected long current;
    protected long pageSize = CommonConst.DEFAULT_PAGE_SIZE;

}
