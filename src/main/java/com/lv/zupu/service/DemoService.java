package com.lv.zupu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lv.zupu.base.redis.RedisCache;
import com.lv.zupu.base.redis.StringRedisCache;
import com.lv.zupu.config.jackson.JacksonXml;
import com.lv.zupu.config.jackson.ObjectMapperJson;
import com.lv.zupu.domain.FamilyBo;
import com.lv.zupu.entity.FamilyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2018/7/12 16:33
 * @Version: 1.0
 * modified by:
 */
@Service
public class DemoService {
    @Autowired
    private JacksonXml jacksonXml;
    @Autowired
    private ObjectMapperJson objectMapperJson;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private StringRedisCache stringRedisCache;
    @Autowired
    private ExecutorService executorService;

    public String demoJson(FamilyVo familyVo) throws JsonProcessingException {
//        System.out.println(redisCache.remove("familyVo_1111"));
//        redisCache.set("familyVo_1112",familyVo,600, TimeUnit.SECONDS);
        redisCache.set("familyVo_1112",objectMapperJson.obj2string(familyVo),600, TimeUnit.SECONDS);
        //不能直接转换
        FamilyBo familyBo = (FamilyBo) redisCache.get("familyVo_1112");
        return jacksonXml.objToxml( redisCache.get("familyVo_1112"));
    }

    public void aysnThreadPool(){
        System.out.println(Thread.currentThread().getName()+"主线程");
        executorService.submit(()->{
            /*try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            System.out.println(Thread.currentThread().getName()+"线程池异步执行.............");
        });
    }
}
