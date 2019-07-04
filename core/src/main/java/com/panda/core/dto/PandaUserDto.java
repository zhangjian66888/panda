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
public class PandaUserDto extends BaseDto {

    private Long businessLineId;
    private String businessLineName;

    private Long groupId;

    private String groupName;

    private String username;

    private Integer userType;
    private String userTypeShow;

    private String password;

    private String zhName;

    private String mobile;

    private String email;

}
