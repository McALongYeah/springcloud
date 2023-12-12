package com.yc.config;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice    //  Controller控制器,ioc,     Advice: aop中的增强
@Order(-100000)    // 切面的顺序
//   AOP技术
public class CustomerExceptionHandler {
    @ExceptionHandler(RuntimeException.class) //do层 ->service（事务回滚针对runtimeException）
    @ResponseBody
    public Map<String,Object> handleRuntimeException(RuntimeException exception){
        Map<String,Object> map=new HashMap<>();
        map.put("code",0);
        map.put("msg","异常信息:" + exception.getCause());
        return map;
    }
}