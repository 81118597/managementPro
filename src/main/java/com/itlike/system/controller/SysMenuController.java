package com.itlike.system.controller;


import com.itlike.system.entity.SysMenu;
import com.itlike.system.entity.TreeVo;
import com.itlike.system.entity.query.RoleMenuQuery;
import com.itlike.system.entity.query.RoleMenuTreeQuery;
import com.itlike.system.service.SysMenuService;
import com.itlike.utils.Result;
import com.itlike.utils.jwt.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 菜单信息表 前端控制器
 * </p>
 *
 * @author ZYQ
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/api/menu")
public class SysMenuController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysMenuService sysMenuService;
    @GetMapping("/getMenuList")
    public Result getMenuList(){
        List<SysMenu> sysMenus = sysMenuService.queryList();
        return Result.ok().data("list",sysMenus);
    }
    @PostMapping("/addMenu")
    public Result addMenu(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return Result.ok();
    }
    @GetMapping("/{MenuId}")
    public Result getInfo(@PathVariable Long MenuId){
        SysMenu sysMenu=sysMenuService.getInfo(MenuId);
        return Result.ok().data("Menu",sysMenu);
    }
    @PutMapping("/updaMenu")
    public Result updaMenu(@RequestBody SysMenu sysMenu){
        sysMenuService.updateById(sysMenu);
        return Result.ok();
    }
    @DeleteMapping("/del/{MenuId}")
    public Result del(@PathVariable Long MenuId){
        sysMenuService.removeByChiId(MenuId);
        return Result.ok();
    }
    @GetMapping("/Menu")
    public Result Menu(){
        List<TreeVo> menus=sysMenuService.Menu();
        return Result.ok().data("menus",menus);
    }
    @PostMapping("/saveAssignRole")
    public Result saveAssignRole(@RequestBody RoleMenuTreeQuery query){
        sysMenuService.saveAssignRole(query);
        return Result.ok();
    }
    @PostMapping("/MenuTree")
    public Result MenuTree(@RequestBody RoleMenuQuery query){
        List<TreeVo> treeVos = sysMenuService.MenuTree(query);
        return Result.ok().data("treeVos",treeVos);
    }
}

