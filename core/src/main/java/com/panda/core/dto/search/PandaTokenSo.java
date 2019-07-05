package com.panda.core.dto.search;

import com.panda.common.dto.BaseSo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * com.panda.core.dto.search.PandaTokenSo
 * <p>
 * DATE 2019/7/4
 *
 * @author zhanglijian.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PandaTokenSo extends BaseSo {

    private Long userId;

    private String accessToken;

    private LocalDateTime expireTime;
}
