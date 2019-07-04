package com.panda.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * com.panda.core.dto.SelectedTagDto
 * <p>
 * DATE 2019/7/2
 *
 * @author zhanglijian.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectedTagDto {

    private Long value;
    private String label;
    private String title;

}
