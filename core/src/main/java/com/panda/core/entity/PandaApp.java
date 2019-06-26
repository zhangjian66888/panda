package com.panda.core.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.panda.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 应用
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PandaApp extends BaseEntity {

    /**
     * 应用名
     */
    private String appName;
    /**
     * 应用别名
     */
    private String appAlias;

    private Integer appCode;

    private Integer appLevel;

    /**
     * 业务线
     */
    private Long businessLineId;

}
