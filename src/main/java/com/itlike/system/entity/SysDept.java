package com.itlike.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysMenu对象", description="部门信息表")
public class SysDept implements Serializable{

    @ApiModelProperty(value = "部门 ID")
    @TableId(value = "id")
    private String id;

    @ApiModelProperty(value = "上级部门 ID")
    private String pid;

    @ApiModelProperty(value = "上级部门名称")
    private String parentName;

    @ApiModelProperty(value = "部门经理")
    private String manager;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "部门编码")
    private String deptCode;

    @ApiModelProperty(value = "部门地址")
    private String deptAddress;

    @ApiModelProperty(value = "部门电话")
    private String deptPhone;

    @ApiModelProperty(value = "上级部门ID集集合")
    private String likeId;

    @ApiModelProperty(value = "部门序号")
    private Integer orderNum;

}
