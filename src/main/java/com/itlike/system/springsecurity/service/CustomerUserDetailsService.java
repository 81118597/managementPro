package com.itlike.system.springsecurity.service;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.itlike.system.entity.SysMenu;
import com.itlike.system.service.SysMenuService;
import com.itlike.system.entity.SysUser;
import com.itlike.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义认证类
 */
@Component
public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserService.getUserByUserName(username);
        if(user==null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        List<SysMenu> sysMenus = sysMenuService.selectPermissionByUserId(user.getId());
        if(CollectionUtils.isEmpty(sysMenus)||sysMenus.get(0)==null){
            return null;
        }
        //封装权限信息(权限标识符code)
        List<GrantedAuthority> authorities=null;
        if(!CollectionUtils.isEmpty(sysMenus)){
            authorities=new ArrayList<>();
            for (SysMenu menu:sysMenus){
                String code=menu.getCode();
                authorities.add(new SimpleGrantedAuthority(code));
            }
        }
        user.setAuthorities(authorities);
        user.setMenuList(sysMenus);
        return user;
    }
}
