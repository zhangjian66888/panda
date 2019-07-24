package com.panda.common.dto;

import lombok.*;

/**
 * com.panda.common.dto.ResultDto
 * <p>
 * DATE 2019/7/24
 *
 * @author zhanglijian.
 */

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto<T> {

    public static Integer CODE_SUCESS = 0;
    public static Integer CODE_ERROR = 1;
    public static Integer CODE_NOT_LOGIN = -100;
    public static String CODE_SUCESS_MSG = "操作成功";
    public static String CODE_ERROR_MSG = "操作失败";

    private Integer code;
    private String msg;

    private T data;

    public ResultDto setCode(Integer code){
        this.code = code;
        return this;
    }
    public ResultDto setMsg(String msg) {
        this.msg = msg;
        return this;
    }
    public ResultDto setData(T data){
        this.data = data;
        return this;
    }

    public static ResultDto SUCCESS(){
        return ResultDto.builder().code(CODE_SUCESS).msg(CODE_SUCESS_MSG).build();
    }
    public static ResultDto ERROR(){
        return ResultDto.builder().code(CODE_ERROR).msg(CODE_ERROR_MSG).build();
    }

}
