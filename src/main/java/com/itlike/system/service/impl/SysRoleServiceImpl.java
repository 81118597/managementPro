package com.itlike.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itlike.system.entity.SysRole;
import com.itlike.system.entity.UserRole;
import com.itlike.system.entity.query.SysRoleQuery;
import com.itlike.system.mapper.SysRoleMapper;
import com.itlike.system.mapper.UserRoleMapper;
import com.itlike.system.service.SysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public IPage<SysRole> Query(SysRoleQuery sysRoleQuery) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(sysRoleQuery.getName())){
            queryWrapper.lambda().like(SysRole::getName,sysRoleQuery.getName());
        }
        queryWrapper.lambda().orderByDesc(SysRole::getCreateDate);
        return baseMapper.selectPage(sysRoleQuery.getPage(), queryWrapper);
    }

    @Override
    public UserRole getRouleIdByUser(Long userId) {
        return userRoleMapper.getRoleIdByUserId(userId);
    }

    @Override
    public int assignRole(UserRole userRole) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserRole::getUserId,userRole.getUserId());
        userRoleMapper.delete(queryWrapper);
        return userRoleMapper.insert(userRole);
    }
}
