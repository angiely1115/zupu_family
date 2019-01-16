package com.lv.zupu;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * @Author: lvrongzhuan
 * @Description: 日志测试
 * @Date: 2019/1/13 14:18
 * @Version: 1.0
 * modified by:
 */

public class LogTest {

    Logger logger = LoggerFactory.getLogger(LogTest.class);

    /**
     * 简单日志
     */
    @Test
    public void testLog1(){
        logger.info("日日");
    }

    /**
     * 添加自定义属性在日志中
     */
    @Test
    public void test2(){
        MDC.put("traceId","自定义的属性");
        MDC.put("other","other");
        logger.info("zidingyi");
        //用完需要异常掉
        MDC.remove("traceId");
        //清除所有的
        MDC.clear();
        System.out.println(MDC.getMDCAdapter());
        System.out.println(MDC.get("traceId"));
    }
}
