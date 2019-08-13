package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

import javax.xml.transform.Result;

//继承RuntimeException 对代码没有侵入性
public class CustomException extends RuntimeException{

    private ResultCode resultCode;

    public CustomException(ResultCode resultCode){
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode(){
        return this.resultCode;
    }
}
