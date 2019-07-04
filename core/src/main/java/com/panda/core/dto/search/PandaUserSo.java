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
public class PandaUserSo extends BaseSo {

    private Long businessLineId;

    private Long groupId;

    private Integer userType;

    private String username;

    private String zhName;

    private String mobile;

    private String email;
}
