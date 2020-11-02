package com.jw.bigwhalemonitor.handler;

import com.jw.bigwhalemonitor.common.pojo.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Msg errorHandler(Exception e) {
        logger.error("全局统一的异常处理" + e.getMessage());
        return Msg.create(-999, "全局异常处理", e.getMessage());
    }
}
