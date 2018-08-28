package com.lv.zupu.utils;

public enum AsyncPoolEnum {
CORE_POOL_SIZE(10), MAX_POOL_SIZE(128), ALIVE_TIME(5);
public Integer val;

AsyncPoolEnum(Integer val) {
this.val = val;
}
}