package com.itlike.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysUser对象", description="用户信息表")
public class SysUser implements Serializable,UserDetails {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户 ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码，加密存储, admin/1234")
    private String password;

    @ApiModelProperty(value = "帐户是否过期(1 未过期，0已过期)")
    private Integer isAccountNonExpired;

    @ApiModelProperty(value = "帐户是否被锁定(1 未过期，0已过期)")
    private Integer isAccountNonLocked;

    @ApiModelProperty(value = "密码是否过期(1 未过期，0已过期)")
    private Integer isCredentialsNonExpired;

    @ApiModelProperty(value = "帐户是否可用(1 可用，0 删除用户)")
    private Integer isEnabled;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像url")
    private String imageUrl;

    @ApiModelProperty(value = "注册手机号")
    private String mobile;

    @ApiModelProperty(value = "注册邮箱")
    private String email;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

    @ApiModelProperty(value = "密码更新时间")
    private Date pwdUpdateDate;

    @ApiModelProperty(value = "部门Id")
    private String deptId;

    @TableField(exist = false)
    private List<SysMenu> menuList;

    @TableField(exist = false)
    Collection<? extends GrantedAuthority> authorities;

    @TableField(exist = false)
    private String deptName;
    //帐户是否过期(1 未过期，0已过期)
    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired==1?true:false;
    }
    //帐户是否被锁定(1 未过期，0已过期)
    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked==1?true:false;
    }
    //密码是否过期(1 未过期，0已过期)
    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired==1?true:false;
    }
    //帐户是否可用(1 可用，0 删除用户)
    @Override
    public boolean isEnabled() {
        return this.isEnabled==1?true:false;
    }

    }
