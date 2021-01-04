package com.itlike.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itlike.system.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
    UserRole getRoleIdByUserId(@Param("userId") Long userId);
}
