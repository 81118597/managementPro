package com.itlike.system.entity.query;

import com.itlike.system.entity.TreeVo;
import lombok.Data;

import java.util.List;

@Data
public class RoleMenuTreeQuery {

    private List<TreeVo> list;

    private Long roleId;
}
