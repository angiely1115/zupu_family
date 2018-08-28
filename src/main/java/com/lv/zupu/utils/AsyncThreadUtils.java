package com.lv.zupu.utils;

import java.util.concurrent.*;

public class AsyncThreadUtils {
private static AsyncThreadUtils instance = null;
private static Object metux = new Object();
private ThreadPoolExecutor executor = null;

private AsyncThreadUtils() {
this.executor = new ThreadPoolExecutor(
AsyncPoolEnum.CORE_POOL_SIZE.val.intValue(),
AsyncPoolEnum.MAX_POOL_SIZE.val.intValue(),
AsyncPoolEnum.ALIVE_TIME.val.intValue(), TimeUnit.SECONDS,
new LinkedBlockingQueue(100));
}

public static AsyncThreadUtils getInstance() {
if (instance == null) {
synchronized (metux) {
if (instance == null) {
instance = new AsyncThreadUtils();
}
}
}
return instance;
}
public Future submit(Callable able) {
return this.executor.submit(able);
}
public void excute(Runnable runnable) {
this.executor.execute(runnable);
}
}