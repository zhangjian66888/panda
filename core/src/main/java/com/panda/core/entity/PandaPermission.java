package com.panda.core.entity;

import com.panda.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
public class PandaPermission extends BaseEntity {

    private String name;

    private String showName;

    private String url;

    private String method;

    private Integer type;

    private String action;

    private Boolean menuType;

    private Long parentId;

    private String menuIcon;

    private Integer menuSequence;

    private String description;
}
