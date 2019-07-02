package com.panda.core.dto;

import com.panda.common.dto.BaseDto;
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
public class PandaPermissionDto extends BaseDto {

    private String name;

    private String showName;

    private Long businessLineId;

    private String businessLineName;

    private Long appCode;

    private String appName;

    private String url;

    private String method;

    private Integer type;

    private String typeShow;

    private String action;

    private Integer menuType;

    private String menuTypeShow;

    private Long parentId;

    private String menuIcon;

    private Integer menuSequence;

    private String description;
}
