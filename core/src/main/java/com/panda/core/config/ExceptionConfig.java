package com.panda.core.config;

import com.panda.common.dto.ResultDto;
import com.panda.common.exception.AuthException;
import com.panda.common.exception.LoginException;
import com.panda.common.exception.PandaException;
import com.panda.common.exception.PermissionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Demo class
 *
 * @author zhanglijian
 * @date 2019/06/31
 */
@ControllerAdvice
public class ExceptionConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 业务异常
     *
     * @param e base exception
     * @return http response√
     */
    @ExceptionHandler(AuthException.class)
    public HttpEntity<ResultDto> handlerException(final AuthException e) {
        return new ResponseEntity<>(ResultDto
                .builder()
                .code(ResultDto.CODE_ERROR)
                .msg(e.getMessage())
                .build(),
                HttpStatus.OK);
    }

    /**
     * 业务异常
     *
     * @param e base exception
     * @return http response√
     */
    @ExceptionHandler(PandaException.class)
    public HttpEntity<ResultDto> handlerException(final PandaException e) {
        return new ResponseEntity<>(ResultDto.builder()
                .code(ResultDto.CODE_ERROR)
                .msg(e.getMessage())
                .build(),
                HttpStatus.OK);
    }

    /**
     * 未登录异常
     *
     * @param e base exception
     * @return http response√
     */
    @ExceptionHandler(LoginException.class)
    public HttpEntity<ResultDto> handlerException(final LoginException e) {
        return new ResponseEntity<>(ResultDto.builder()
                .code(ResultDto.CODE_ERROR)
                .msg(e.getMessage())
                .build(),
                HttpStatus.UNAUTHORIZED);
    }


    /**
     * 权限异常
     *
     * @param e base exception
     * @return http response√
     */
    @ExceptionHandler(PermissionException.class)
    public HttpEntity<ResultDto> handlerException(final PermissionException e) {
        return new ResponseEntity<>(ResultDto.builder()
                .code(ResultDto.CODE_ERROR)
                .msg(e.getMessage())
                .build(),
                HttpStatus.FORBIDDEN);
    }

    /**
     * 校验异常
     *
     * @param e base exception
     * @return http response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HttpEntity<ResultDto> handlerException(final MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuffer sb = new StringBuffer();
        for (ObjectError objectError : bindingResult.getAllErrors()) {
            sb.append(objectError.getDefaultMessage());
        }
        return new ResponseEntity<>(ResultDto.builder()
                .code(ResultDto.CODE_ERROR)
                .msg(sb.toString())
                .build(),
                HttpStatus.OK);
    }

    /**
     * 校验异常
     *
     * @param e base exception
     * @return http response
     */
    @ExceptionHandler(ServletRequestBindingException.class)
    public HttpEntity<ResultDto> handlerException(final ServletRequestBindingException e) {
        return new ResponseEntity<>(ResultDto.builder()
                .code(ResultDto.CODE_ERROR)
                .msg(e.getMessage())
                .build(),
                HttpStatus.OK);
    }


    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public HttpEntity<?> handlerException(final HttpMediaTypeNotAcceptableException e) {
        return new ResponseEntity<>(ResultDto.builder()
                .code(ResultDto.CODE_ERROR)
                .msg("maybe db error")
                .build(),
                HttpStatus.OK);
    }

    @ExceptionHandler(IllegalStateException.class)
    public HttpEntity<ResultDto> handlerException(final IllegalStateException e) {
        return new ResponseEntity<>(ResultDto.builder()
                .code(ResultDto.CODE_ERROR)
                .msg(e.getMessage())
                .build(),
                HttpStatus.OK);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public HttpEntity<ResultDto> handlerException(final DataIntegrityViolationException e) {
        logger.error("DataIntegrityViolationException===================", e);
        return new ResponseEntity<>(ResultDto.builder()
                .code(ResultDto.CODE_ERROR)
                .msg("maybe db error")
                .build(),
                HttpStatus.OK);
    }

    /**
     * server 异常
     *
     * @param e server exception
     * @return http response
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public HttpEntity<ResultDto> handlerHttpMessageNotReadableException(final HttpMessageNotReadableException e) {

        logger.error("Exception===================", e);
        return new ResponseEntity<>(ResultDto.builder()
                .code(ResultDto.CODE_ERROR)
                .msg("请求参数有误")
                .build(),
                HttpStatus.OK);
    }

    /**
     * server 异常
     *
     * @param e server exception
     * @return http response
     */
    @ExceptionHandler(Exception.class)
    public HttpEntity<ResultDto> handlerException(final Exception e) {

        logger.error("Exception===================", e);
        return new ResponseEntity<>(ResultDto.builder()
                .code(ResultDto.CODE_ERROR)
                .msg("Internal Server Error")
                .build(),
                HttpStatus.OK);
    }


}
