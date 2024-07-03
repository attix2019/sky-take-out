package com.sky.handler;

import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler
    public Result duplicateNameHandler(SQLIntegrityConstraintViolationException ex){
        String message = ex.getMessage();
        if(message.contains("Duplicate entry")){
            String key = message.split(" ")[2].replaceAll("\'","");
            log.error( "账号为 " + key + " 的员工已经存在");
            return Result.error("账号为 " + key + " 的员工已经存在");
        }
        return  Result.error("未知错误");
    }

}
