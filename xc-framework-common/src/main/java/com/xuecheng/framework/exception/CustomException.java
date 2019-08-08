package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

import javax.xml.transform.Result;

public class CustomException extends RuntimeException{

    private ResultCode resultCode;

    public CustomException(ResultCode resultCode){

    }
}
