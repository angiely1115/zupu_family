package com.lv.zupu.base.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2018/7/23 19:31
 * @Version: 1.0
 * modified by:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyLog {
    String[] value();
}
