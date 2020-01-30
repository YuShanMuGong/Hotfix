package com.mu.hotfix.srv.advice;

import com.mu.hotfix.common.DTO.ResultDTO;
import com.mu.hotfix.common.constants.ErrorCodes;
import com.mu.hotfix.srv.exception.HotFixSrvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandleAdvice {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultDTO<?> handleException(Exception e){
        logger.error("handleException",e);
        if(e instanceof HotFixSrvException){
            HotFixSrvException he = (HotFixSrvException) e;
            return ResultDTO.fail(he.getCode(),he.getMsg());
        }
        return ResultDTO.fail(ErrorCodes.UN_KNOW_EXCEPTION,e.getMessage());
    }

}
