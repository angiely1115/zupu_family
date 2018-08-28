package com.lv.zupu.config.web;

import com.lv.zupu.base.BaseResult;
import com.lv.zupu.base.ResultCode;
import com.lv.zupu.base.log.LogContextHolder;
import com.lv.zupu.config.SpringContextConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2018/7/11 17:40
 * @Version: 1.0
 * modified by:
 */
@RestControllerAdvice
@ConditionalOnWebApplication
public class AdviceErrorConfig {
    private static final Logger log = LoggerFactory.getLogger(AdviceErrorConfig.class);

    public AdviceErrorConfig() {
    }
    @ExceptionHandler({Throwable.class})
    public BaseResult handleThrowable(Throwable e) {
        logError("Throwable", e);
        return BaseResult.fail();
    }

    @ExceptionHandler({SQLException.class})
    public BaseResult handleSqlException(SQLException e) {
        AdviceErrorConfig.logError("SQLException", e);
        return BaseResult.fail(ResultCode.SQL_ERROR.getCode(), ResultCode.SQL_ERROR.getMsg());
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public BaseResult handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        AdviceErrorConfig.logWarn("HttpMediaTypeNotSupportedException", e);
        return BaseResult.fail(ResultCode.HTTP_MEDIA_TYPE_NOT_SUPPORTED_ERROR.getCode(), ResultCode.HTTP_MEDIA_TYPE_NOT_SUPPORTED_ERROR.getMsg());
    }
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public BaseResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        AdviceErrorConfig.logWarn("MissingServletRequestParameterException", e);
        return BaseResult.fail(ResultCode.PARAM_ERROR.getCode(), String.format("参数%s未传", e.getParameterName()));
    }
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public BaseResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        AdviceErrorConfig.logWarn("HttpRequestMethodNotSupportedException", e);
        return BaseResult.fail(ResultCode.HTTP_METHOD_NOT_ALLOW_ERROR.getCode(), ResultCode.HTTP_METHOD_NOT_ALLOW_ERROR.getMsg());
    }
    @ExceptionHandler({BindException.class})
    public BaseResult handBindException(BindException e) {
        AdviceErrorConfig.logWarn("BindException", e);
        BindingResult bindingResult = e.getBindingResult();
        return BaseResult.fail(ResultCode.PARAM_ERROR.getCode(), (String)bindingResult.getAllErrors().stream().findFirst().map(DefaultMessageSourceResolvable::getDefaultMessage).orElse(""));
    }
    private static void logError(String name, Throwable e) {
        LogContextHolder.setCurrentLogResponse(true);
        if (SpringContextConfig.containsBean("tracer")) {
           /* Tracer tracer = (Tracer)SpringContextConfig.getBean(Tracer.class);
            if (tracer.currentSpan() != null) {
                tracer.currentSpan().tag("error", e.getMessage());
            }*/
        }
        log.error("{}", name, e);
    }
    private static void logWarn(String name, Throwable e) {
        log.warn("{}", name, e);
    }
}
