package com.itlike.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itlike.system.entity.SysRole;
import com.itlike.system.entity.query.SysRoleQuery;
import com.itlike.system.mapper.SysRoleMapper;
import com.itlike.system.service.SysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Override
    public IPage<SysRole> Query(SysRoleQuery sysRoleQuery) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(sysRoleQuery.getName())){
            queryWrapper.lambda().like(SysRole::getName,sysRoleQuery.getName());
        }
        queryWrapper.lambda().orderByDesc(SysRole::getCreateDate);
        return baseMapper.selectPage(sysRoleQuery.getPage(), queryWrapper);
    }
}
