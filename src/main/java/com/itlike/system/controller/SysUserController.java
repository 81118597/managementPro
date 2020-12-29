package com.itlike.system.controller;

import com.itlike.system.entity.SysUser;
import com.itlike.system.service.SysUserService;
import com.itlike.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @GetMapping("/userList")
    public Result userList(){
        SysUser byId = sysUserService.getById(9);
        return Result.ok().data("list",byId);
    }
    @GetMapping("/code/image")
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        sysUserService.imageCode(request,response);
    }
}
