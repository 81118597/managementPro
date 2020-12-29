package com.itlike.system.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class TreeVo implements Serializable {
    private Long id;

    private Long pid;

    private String name;

    private boolean open;

    private boolean checked;
}
