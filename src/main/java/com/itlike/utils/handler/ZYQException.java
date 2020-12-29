package com.itlike.utils.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZYQException {
    private Integer code;//状态码
    private String msg;//异常信息
}
