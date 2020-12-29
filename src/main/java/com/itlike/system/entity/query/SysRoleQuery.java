package com.itlike.system.entity.query;

import com.itlike.system.entity.SysRole;
import com.itlike.utils.BaseRequest;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SysRoleQuery extends BaseRequest<SysRole> {

    private String name;
}
