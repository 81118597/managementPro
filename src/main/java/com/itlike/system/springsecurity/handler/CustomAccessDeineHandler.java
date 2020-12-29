package com.itlike.system.springsecurity.handler;

import com.alibaba.fastjson.JSONObject;
import com.itlike.utils.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证用户无权限访问处理器
 */
@Component
public class CustomAccessDeineHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        JSONObject json = new JSONObject();
        json.put("data", Result.error().message("没有访问权限!").code(HttpStatus.FORBIDDEN.value()));
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(json.toString());
    }
}
