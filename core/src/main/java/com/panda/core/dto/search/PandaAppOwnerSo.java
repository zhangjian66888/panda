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
 * @since 2019-07-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PandaAppOwnerSo extends BaseSo {

    private Long appCode;

    private Integer ownerType;
}
