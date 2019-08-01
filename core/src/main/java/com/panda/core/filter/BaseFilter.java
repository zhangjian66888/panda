package com.panda.core.filter;

import com.alibaba.fastjson.JSON;
import com.panda.common.dto.StatusDto;
import com.panda.common.exception.PandaFilterException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * com.panda.core.filter.BaseFilter
 * <p>
 * DATE 2019/8/1
 *
 * @author zhanglijian.
 */
public abstract class BaseFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            doingFilter(request, response);
        } catch (PandaFilterException e) {
            handlerException(response, e);
            return;
        }
        chain.doFilter(request, response);
    }

    public abstract void doingFilter(HttpServletRequest request, HttpServletResponse response) throws PandaFilterException;

    private void handlerException(HttpServletResponse response, PandaFilterException e) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(e.getStatus());
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(JSON.toJSONString(StatusDto.builder().code(StatusDto.CODE_ERROR).msg(e.getMessage()).build()));
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
