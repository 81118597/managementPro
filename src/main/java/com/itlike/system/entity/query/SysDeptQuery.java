package com.itlike.system.entity.query;

import com.itlike.system.entity.SysDept;
import com.itlike.utils.BaseRequest;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SysDeptQuery extends BaseRequest<SysDept> {

    private String name;

    private String iphone;

    private String pid;
}
