package com.itlike.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itlike.system.entity.RoleMenu;
import com.itlike.system.entity.SysMenu;
import com.itlike.system.entity.SysUser;
import com.itlike.system.entity.TreeVo;
import com.itlike.system.entity.query.RoleMenuQuery;
import com.itlike.system.entity.query.RoleMenuTreeQuery;
import com.itlike.system.mapper.RoleMenuMapper;
import com.itlike.system.mapper.SysMenuMapper;
import com.itlike.system.service.SysMenuService;
import com.itlike.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单信息表 服务实现类
 * </p>
 *
 * @author ZYQ
 * @since 2020-12-22
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private SysUserService sysUserService;
    @Override
    public List<SysMenu> queryList() {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda().orderByDesc(SysMenu::getSort);
        List<SysMenu> sysMenus = baseMapper.selectList(wrapper);
        return makeTree(sysMenus,0L);
    }

    @Override
    public List<SysMenu> selectPermissionByUserId(Long userId) {
        return baseMapper.selectPermissionByUserId(userId);
    }

    @Override
    public List<SysMenu> findByRoleId(Long roleId) {
        return baseMapper.findByRoleId(roleId);
    }
    @Override
    public  List<SysMenu> makeTree(List<SysMenu> menuList,Long pid){
        //1.查询所有的pid的子类
        List<SysMenu> children = menuList.stream().filter(item -> item.getParentId().equals(pid)).collect(Collectors.toList());
        //2.查询所有的非pid的子类
        List<SysMenu> collect = menuList.stream().filter(item -> item.getParentId()!=pid).collect(Collectors.toList());
        if(children.size()>0){
            children.forEach(x->{
                if(collect.size()>0){
                    makeTree(collect,x.getId()).forEach(y->x.getChildren().add(y)
                    );
                }
            });
        }
        return children;
    }

    @Override
    public List<TreeVo> Menu() {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysMenu::getType,0).or().eq(SysMenu::getType,1);
        List<SysMenu> menus = baseMapper.selectList(queryWrapper);
        List<TreeVo> treeList=new ArrayList<>();
        TreeVo treeVo = new TreeVo();
        treeVo.setId(0L);
        treeVo.setPid(-1L);
        treeVo.setName("顶级菜单");
        treeVo.setOpen(true);
        treeVo.setChecked(true);
        treeList.add(treeVo);
        for(SysMenu menu:menus){
            TreeVo tree = new TreeVo();
            tree.setId(menu.getId());
            tree.setPid(menu.getParentId());
            tree.setName(menu.getLabel());
            tree.setOpen(true);
            tree.setChecked(true);
            treeList.add(tree);
        }
        return treeList;
    }

    @Override
    public int removeByChiId(Long menuId) {
        ArrayList<Long> idList = new ArrayList<>();
        this.selectChildListById(menuId, idList);
        idList.add(menuId);
        int row = baseMapper.deleteBatchIds(idList);
        return row;
    }

    @Override
    public SysMenu getInfo(Long menuId) {
        SysMenu sysMenu = baseMapper.selectById(menuId);
        SysMenu Parent = sysMenuMapper.selectById(sysMenu.getParentId());
        if(Parent==null){
            sysMenu.setParentId(0L);
            sysMenu.setParentName("顶级菜单");
        }else{
            sysMenu.setParentName(Parent.getLabel());
        }
        return sysMenu;
    }
    @Transactional
    @Override
    public int saveAssignRole(RoleMenuTreeQuery query) {
        List<TreeVo> list = query.getList();
        Long roleId = query.getRoleId();
        List<Long> collect = list.stream().filter(item -> item != null).map(item -> item.getId()).collect(Collectors.toList());
        QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(RoleMenu::getRoleId,roleId);
        roleMenuMapper.delete(wrapper);
        return roleMenuMapper.saveRolePermissions(query.getRoleId(),collect);
    }

    @Override
    public List<TreeVo> MenuTree(RoleMenuQuery query) {
        //1.查询当前用户的所有权限
        SysUser user = sysUserService.getById(query.getUserId());
        List<SysMenu> menus = baseMapper.selectList(null);

        List<SysMenu> menuList = baseMapper.findByRoleId(query.getRoleId());
        List<TreeVo> listTree=new ArrayList<>();
        for (SysMenu menu : menus) {
            TreeVo treeVo=new TreeVo();
            treeVo.setId(menu.getId());
            treeVo.setPid(menu.getParentId());
            treeVo.setName(menu.getLabel());
            if(menuList.size()>0){
                for (SysMenu sysMenu : menuList) {
                    if(menu.getId().equals(sysMenu.getId())){
                        treeVo.setChecked(true);
                        break;
                    }
                }
            }
            listTree.add(treeVo);
        }
        return listTree;
    }

    public void selectChildListById(Long id,List<Long> idList){
        List<SysMenu> menus = baseMapper.selectList(new QueryWrapper<SysMenu>().lambda().eq(SysMenu::getParentId,id).select(SysMenu::getId));
        menus.stream().forEach(item->{
            idList.add(item.getId());
            this.selectChildListById(item.getId(),idList);
        });
    }
}
