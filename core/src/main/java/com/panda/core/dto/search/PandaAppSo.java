package com.panda.core.dto.search;

import com.panda.common.dto.BaseSo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * com.panda.core.dto.search.PandaAppSearchDto
 * <p>
 * DATE 2019/6/4
 *
 * @author zhanglijian.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PandaAppSo extends BaseSo {

    private String appName;
    private String appAlias;
    private Integer appLevel;
    private Long businessLineId;
}
