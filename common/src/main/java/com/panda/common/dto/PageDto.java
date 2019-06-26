package com.panda.common.dto;

import com.panda.common.consts.CommonConst;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * <p>
 * DATE 2018/4/19.
 *
 * @author zhanglijian.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    protected long total = 0;
    protected long current;
    protected long pageSize = CommonConst.DEFAULT_PAGE_SIZE;
    private List<T> records = new ArrayList();
}
