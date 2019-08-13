package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionCatch {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);

    //使用EXCEPTIONS 存放异常类型和错误代码的映射,ImmutableMap的特点是一旦创建不可改变,并且线程安全
    private static ImmutableMap<String,ResultCode> EXCEPTIONS;
    //使用builder 来构建一个异常类型和错误代码的异常
    protected  static ImmutableMap.Builder<String,ResultCode> builder = ImmutableMap.builder();

    static {
        builder.put(HttpMessageNotReadableException.class.getName(),CommonCode.INVALID_PARAM);
    }

    //注意这里的返回结果是要传递给前端的'
    @ExceptionHandler(CustomException.class) //捕获CustomException类型的异常
    @ResponseBody //以json的格式返回给前端
    public ResponseResult customeException(CustomException e) {
        LOGGER.error("ExceptionCatch.customeException catch customException : {}\r\nexception: ",e.getMessage(),e);

        ResultCode resultCode = e.getResultCode();

        ResponseResult responseResult = new ResponseResult(resultCode);
        return responseResult;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception e){
        LOGGER.error("ExceptionCatch.exception catch Exception : {} ",e.getMessage());

        if (EXCEPTIONS == null){
            EXCEPTIONS = builder.build();
        }
        String name = "";
        if (null != e.getClass()) {
             name = e.getClass().getName();
        }

        final ResultCode sysCode = EXCEPTIONS.get(name);
        final ResponseResult responseResult;
        if (sysCode != null){
            responseResult = new ResponseResult(sysCode);
        } else {
            responseResult = new ResponseResult(CommonCode.SERVER_ERROR);
        }

        return responseResult;
    }
}
