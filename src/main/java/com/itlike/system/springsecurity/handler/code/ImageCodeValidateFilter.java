package com.itlike.system.springsecurity.handler.code;


import com.itlike.system.springsecurity.exception.TokenException;
import com.itlike.system.springsecurity.exception.ValidateCodeException;
import com.itlike.system.springsecurity.handler.CustomAuthenticationFailureHandler;
import com.itlike.system.service.SysUserService;
import com.itlike.system.service.impl.SysUserServiceImpl;
import com.itlike.system.springsecurity.service.CustomerUserDetailsService;
import com.itlike.utils.jwt.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * OncePerRequestFilter: 所有请求之前被调用一次
 */
@Component
public class ImageCodeValidateFilter extends OncePerRequestFilter {
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private JwtTokenUtil jwt;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(httpServletRequest.getMethod().equalsIgnoreCase("post")&&"/api/user/login".equals(httpServletRequest.getRequestURI())){
            try {
                validate(httpServletRequest);
            }catch (AuthenticationException e){
                customAuthenticationFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
                return;
            }
        }else {
            String uri = httpServletRequest.getRequestURI();
            if(!uri.equals("/api/user/code/image")){
                try {
                    validationToken(httpServletRequest);
                }catch (AuthenticationException e){
                    customAuthenticationFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
                    return;
                }
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
    public void validationToken(HttpServletRequest request) throws IOException {
        String header = request.getHeader("token");
        String token =(String)redisTemplate.opsForValue().get("token");
        if(!header.equals(token)){
            throw new TokenException("验证token失败");
        }
        String name = jwt.getUsernameFromToken(token);
        if(StringUtils.isEmpty(token)||StringUtils.isEmpty(name)){
            throw new TokenException("验证token失败");
        }
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(name);
        if(userDetails==null){
            throw new TokenException("验证token失败");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    //验证码
    public void validate(HttpServletRequest request) throws IOException {
        //先获取seesion的验证码
        String sessionCode =(String) request.getSession().getAttribute(SysUserServiceImpl.SESSION_KEY);
        //获取用户输入的验证码
        String code = request.getParameter("verification");
        if(StringUtils.isBlank(code)){
            throw new ValidateCodeException("验证码不能为空");
        }
        if(!sessionCode.equalsIgnoreCase(code)){
            throw new ValidateCodeException("验证码输入错误");
        }
    }
}
