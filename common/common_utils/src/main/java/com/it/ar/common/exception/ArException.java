package com.it.ar.common.exception;

import com.it.ar.common.result.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "自定义全局异常类")
public class ArException extends RuntimeException{

    @ApiModelProperty(value = "异常状态码")
    private Integer code;

    /*
    * 通过状态码和错误信息创建异常对象
    * */
    public ArException(String message, Integer code){
        super(message);
        this.code = code;
    }
    /*
    * 接受枚举类型对象
    * */
    public ArException(ResultCodeEnum resultCodeEnum){
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString(){
        return "ArException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }

}
