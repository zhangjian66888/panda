package com.panda.core.dto.search;

import com.panda.common.dto.BaseSo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PandaPermissionSo extends BaseSo {

    private String name;

    private String showName;

    private String url;

    private String method;

    private Integer type;

    private String action;

    private Boolean menuType;
}
