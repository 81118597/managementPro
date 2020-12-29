package com.itlike.system.springsecurity.handler;

import com.alibaba.fastjson.JSONObject;

import com.itlike.system.springsecurity.exception.TokenException;
import com.itlike.system.springsecurity.exception.ValidateCodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.itlike.utils.*;

/**
 * 认证失败处理器
 */
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        Logger logger= LoggerFactory.getLogger(Logger.class);
        JSONObject json = new JSONObject();
        json.put("data", Result.error().message(e.getMessage()).code(HttpStatus.UNAUTHORIZED.value()));
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            json.put("data", Result.error().message("账户或密码错误!"));
        } else if (e instanceof LockedException) {
            json.put("data", Result.error().message("账户被锁定，请联系管理员!"));
        } else if (e instanceof CredentialsExpiredException) {
            json.put("data", Result.error().message("证书过期，请联系管理员!"));
        } else if (e instanceof AccountExpiredException) {
            json.put("data", Result.error().message("账户过期，请联系管理员!"));
        } else if (e instanceof DisabledException) {
            json.put("data", Result.error().message("账户被禁用，请联系管理员!"));
        }else if(e instanceof ValidateCodeException) {
            json.put("data", Result.error().message(e.getMessage()));
        }else if(e instanceof TokenException){
            json.put("data", Result.error().message(e.getMessage()).code(600));
        }else{
            logger.info(e.getMessage());
            json.put("data", Result.error().message("登录失败!"));
        }
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(json.toString());
    }
}
