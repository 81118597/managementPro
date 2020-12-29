package com.itlike.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itlike.system.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单信息表 Mapper 接口
 * </p>
 *
 * @author ZYQ
 * @since 2020-12-22
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> selectPermissionByUserId(@Param("userId") String userId);

    List<SysMenu> findByRoleId(@Param("roleId") String roleId);
}
