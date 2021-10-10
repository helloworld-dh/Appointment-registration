package com.it.hospital_manage.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "HospitalSet")
@TableName("hospital_set")
public class HospitalSet extends BaseEntity{

    // serialVersionUID保证序列化与反序列化时的版本一致
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "医院编号")
    private String hoscode;

    @ApiModelProperty(value = "签名密钥")
    private String signKey;

    @ApiModelProperty(value = "api基础路径")
    private String apiUrl;

}
