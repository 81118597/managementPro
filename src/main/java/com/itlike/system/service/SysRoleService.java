package com.itlike.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itlike.system.entity.SysRole;
import com.itlike.system.entity.query.SysRoleQuery;

public interface SysRoleService extends IService<SysRole> {
    IPage<SysRole> Query(SysRoleQuery sysRoleQuery);
}
