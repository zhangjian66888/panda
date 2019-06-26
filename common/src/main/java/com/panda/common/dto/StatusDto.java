package com.panda.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Description
 * <p>
 * DATE 2018/4/17.
 *
 * @author zhanglijian.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusDto {

    public static Integer CODE_SUCESS = 0;
    public static Integer CODE_ERROR = 1;
    public static Integer CODE_NOT_LOGIN = -100;
    public static String CODE_SUCESS_MSG = "操作成功";
    public static String CODE_ERROR_MSG = "操作失败";

    private Integer code;
    private String msg;

    private Object data;

    public StatusDto setCode(Integer code){
        this.code = code;
        return this;
    }
    public StatusDto setMsg(String msg) {
        this.msg = msg;
        return this;
    }
    public StatusDto setData(Object data){
        this.data = data;
        return this;
    }

    public static StatusDto SUCCESS(){
        return StatusDto.builder().code(CODE_SUCESS).msg(CODE_SUCESS_MSG).build();
    }
    public static StatusDto ERROR(){
        return StatusDto.builder().code(CODE_ERROR).msg(CODE_ERROR_MSG).build();
    }
}
