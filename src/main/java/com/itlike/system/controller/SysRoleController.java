package com.itlike.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itlike.system.entity.SysRole;
import com.itlike.system.entity.UserRole;
import com.itlike.system.entity.query.SysRoleQuery;
import com.itlike.system.service.SysRoleService;
import com.itlike.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author ZYQ
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/api/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;
    @PostMapping("/roleList")
    public Result roleList(@RequestBody SysRoleQuery sysRoleQuery){
        IPage<SysRole> RoleList=sysRoleService.Query(sysRoleQuery);
        return Result.ok().data("list",RoleList);
    }
    @PostMapping("/addrole")
    public Result addrole(@RequestBody SysRole sysRole){
        sysRoleService.save(sysRole);
        return Result.ok();
    }
    @GetMapping("/{roleId}")
    public Result getInfo(@PathVariable Long roleId){
        SysRole role = sysRoleService.getById(roleId);
        return Result.ok().data("role",role);
    }
    @PutMapping("/updarole")
    public Result updarole(@RequestBody SysRole sysRole){
        sysRoleService.updateById(sysRole);
        return Result.ok();
    }
    @DeleteMapping("/del/{RoleId}")
    public Result del(@PathVariable Long RoleId){
        sysRoleService.removeById(RoleId);
        return Result.ok();
    }
    //————————————————————————————————————分配角色
    @GetMapping("/getRouleIdByUser/{userId}")
    public Result getRouleIdByUser(@PathVariable Long userId){
        UserRole rouleIdByUser = sysRoleService.getRouleIdByUser(userId);
        return Result.ok().data("RoleUser",rouleIdByUser);
    }
    @PostMapping("/assignRole")
    public Result assignRole(@RequestBody UserRole userRole){
        sysRoleService.assignRole(userRole);
        return Result.ok();
    }

}

