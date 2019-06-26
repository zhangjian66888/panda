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

    private String url;

    private String method;

    private Integer menuId;

    private Integer type;

    private String action;

    private Boolean menuType;

    private Long parentId;

    private String menuIcon;

    private Integer menuSequence;

    private String description;
}
