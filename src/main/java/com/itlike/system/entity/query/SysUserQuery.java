package com.itlike.system.entity.query;

import com.itlike.system.entity.SysUser;
import com.itlike.utils.BaseRequest;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SysUserQuery extends BaseRequest<SysUser> {
    private String username;

    private String mobile;

    private String email;

    private String pid;
}
