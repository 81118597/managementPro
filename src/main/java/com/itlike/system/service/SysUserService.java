package com.itlike.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itlike.system.entity.SysUser;
import com.itlike.system.entity.query.SysUserQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface SysUserService extends IService<SysUser> {
    SysUser getUserByUserName(String username);

    void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException;

    IPage<SysUser> Query(SysUserQuery sysUserQuery);
}
