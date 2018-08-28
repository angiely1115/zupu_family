package com.lv.zupu.base.log;

import org.springframework.core.NamedThreadLocal;

public class LogContextHolder {
    private static ThreadLocal<Boolean> CURRENT_LOG_RESPONSE = new NamedThreadLocal("LV_ACCESS_LOG");

    public LogContextHolder() {
    }

    public static boolean currentLogResponse() {
        return ((Boolean)CURRENT_LOG_RESPONSE.get()).booleanValue();
    }

    public static void setCurrentLogResponse(boolean currentLogResponse) {
        CURRENT_LOG_RESPONSE.set(currentLogResponse);
    }

    public static void remove() {
        CURRENT_LOG_RESPONSE.remove();
    }
}