package com.itlike.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itlike.system.entity.SysDept;
import com.itlike.system.entity.SysUser;
import com.itlike.system.entity.query.SysUserQuery;
import com.itlike.system.service.SysDeptService;
import com.itlike.system.service.SysUserService;
import com.itlike.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SysDeptService sysDeptService;
    @GetMapping("/code/image")
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        sysUserService.imageCode(request,response);
    }
    @PostMapping("/getuserList")
    public Result getuserList(@RequestBody SysUserQuery sysUserQuery){
        IPage<SysUser> list=sysUserService.Query(sysUserQuery);
        return Result.ok().data("list",list);
    }
    @PostMapping("/adduser")
    public Result adduser(@RequestBody SysUser sysUser){
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        System.out.println(sysUser);
        sysUserService.save(sysUser);
        return Result.ok();
    }
    @GetMapping("/{userId}")
    public Result info(@PathVariable Long userId){
        SysUser user = sysUserService.getById(userId);
        SysDept dept = sysDeptService.getById(user.getDeptId());
        user.setDeptName(dept.getName());
        return Result.ok().data("user",user);
    }
    @PutMapping("/updauser")
    public Result updauser(@RequestBody SysUser sysUser){
        sysUserService.updateById(sysUser);
        return Result.ok();
    }
    @DeleteMapping("/deluser/{userId}")
    public Result deluser(@PathVariable Long userId){
        sysUserService.removeById(userId);
        return Result.ok();
    }
}
