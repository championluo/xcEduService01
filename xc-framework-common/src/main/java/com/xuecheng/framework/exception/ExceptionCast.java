package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

public class ExceptionCast {

    //公共的异常抛出代码,入参是ResultCode 返回码对象
    public static void cast(ResultCode resultCode) {
        throw new CustomException(resultCode);
    }
}
