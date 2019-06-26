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
public class PandaRoleSo extends BaseSo {

    private String roleName;

    private String description;

}
