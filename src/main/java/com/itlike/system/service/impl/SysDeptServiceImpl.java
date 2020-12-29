package com.itlike.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itlike.system.entity.SysDept;
import com.itlike.system.entity.query.SysDeptQuery;
import com.itlike.system.mapper.SysDeptMapper;
import com.itlike.system.service.SysDeptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
    @Override
    public IPage<SysDept> Query(SysDeptQuery sysDeptQuery) {
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(sysDeptQuery.getName())){
            queryWrapper.lambda().like(SysDept::getName,sysDeptQuery.getName());
        }
        if(StringUtils.isNotEmpty(sysDeptQuery.getIphone())){
            queryWrapper.lambda().eq(SysDept::getDeptPhone,sysDeptQuery.getName());
        }
        if(StringUtils.isNotEmpty(sysDeptQuery.getPid())){
            queryWrapper.lambda().eq(SysDept::getPid,sysDeptQuery.getPid());
        }
        queryWrapper.lambda().orderByDesc(SysDept::getOrderNum);
        return baseMapper.selectPage(sysDeptQuery.getPage(),queryWrapper);
    }

    @Override
    public List<SysDept> dept() {
        List<SysDept> depts = baseMapper.selectList(null);
        SysDept dept = new SysDept();
        dept.setId("0");
        dept.setPid("-1");
        dept.setName("顶级部门");
        dept.setLikeId("0");
        depts.add(dept);
        return depts;
    }
}
