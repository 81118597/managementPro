package com.itlike.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author linlin
 * @since 2021-01-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Region对象", description="")
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "地区id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "地区名称")
    private String name;

    @ApiModelProperty(value = "货币名称")
    private String currency;

    @ApiModelProperty(value = "货币简称")
    @TableField("CurrencyReferred")
    private String currencyreferred;

    @ApiModelProperty(value = "前置货币符号")
    private String symbol;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;


}
