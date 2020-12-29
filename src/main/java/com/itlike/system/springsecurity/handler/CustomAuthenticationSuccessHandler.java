package com.itlike.system.springsecurity.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.itlike.system.entity.SysMenu;
import com.itlike.system.entity.SysUser;
import com.itlike.system.service.SysMenuService;
import com.itlike.utils.Result;
import com.itlike.utils.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 认证成功处理器
 */
@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private JwtTokenUtil jwt;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private RedisTemplate redisTemplate;
     @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        SysUser user =(SysUser) authentication.getPrincipal();
        String token = jwt.generateToken(user);
        List<SysMenu> menuList = user.getMenuList();
        List<String> code = menuList.stream().filter(item -> item != null).map(item -> item.getCode()).collect(Collectors.toList());
        List<SysMenu> MenuList = menuList.stream().filter(item -> item != null && !item.getType().equals(2)).collect(Collectors.toList());
        List<SysMenu> sysMenus = sysMenuService.makeTree(MenuList, 0L);
        List<SysMenu> routerList = menuList.stream().filter(item -> item.getType().equals(1)).collect(Collectors.toList());
        redisTemplate.opsForValue().set("token",token,100, TimeUnit.MINUTES);//30分钟
        HashMap<String, Object> map = new HashMap<>();
        map.put("token",token);
        map.put("code",code);
        map.put("menuList",sysMenus);
        map.put("routerList",routerList);
        Result result = Result.ok().data(map);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(result));
    }
}
