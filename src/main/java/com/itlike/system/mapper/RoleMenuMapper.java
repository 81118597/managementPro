package com.itlike.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itlike.system.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMenuMapper  extends BaseMapper<RoleMenu> {
    int saveRolePermissions(@Param("roleId") Long roleId, @Param("menuId") List<Long> menuIdList);
}
