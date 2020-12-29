package com.itlike.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itlike.system.entity.SysDept;
import com.itlike.system.entity.query.SysDeptQuery;
import com.itlike.system.service.SysDeptService;
import com.itlike.utils.Result;
import com.itlike.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dept")
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;
    @PostMapping("/deptList")
    public Result deptList(@RequestBody SysDeptQuery sysDeptQuery){
        IPage<SysDept> list=sysDeptService.Query(sysDeptQuery);
        return Result.ok().data("list",list);
    }
    @GetMapping("/getLeftTree")
    public Result getLeftTree(){
        List<SysDept> deptList = sysDeptService.list(null);
        return Result.ok().data("deptList",deptList);
    }
    @PostMapping("/adddept")
    public Result adddept(@RequestBody SysDept sysDept){
        sysDept.setId(UUIDUtil.getUniqueIdByUUId());
        sysDeptService.save(sysDept);
        return Result.ok();
    }
    @GetMapping("/{deptId}")
    public Result getInfo(@PathVariable Long deptId){
        SysDept dept = sysDeptService.getById(deptId);
        return Result.ok().data("dept",dept);
    }
    @PutMapping("/updadept")
    public Result updadept(@RequestBody SysDept sysDept){
        sysDeptService.updateById(sysDept);
        return Result.ok();
    }
    @DeleteMapping("/del/{deptId}")
    public Result del(@PathVariable String deptId){
        sysDeptService.removeById(deptId);
        return Result.ok();
    }
    @GetMapping("/dept")
    public Result dept(){
        List<SysDept> depts=sysDeptService.dept();
        return Result.ok().data("depts",depts);
    }

}