package com.itlike.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itlike.system.entity.SysDept;
import com.itlike.system.entity.query.SysDeptQuery;

import java.util.List;

public interface SysDeptService extends IService<SysDept> {
    IPage<SysDept> Query(SysDeptQuery sysDeptQuery);

    List<SysDept> dept();

}
