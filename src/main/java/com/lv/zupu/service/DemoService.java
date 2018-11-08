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

import java.util.Date;
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


    public FamilyVo redisTest(){
        FamilyVo familyVo = new FamilyVo();
        familyVo.setFamilyName("吕世春");
        familyVo.setCreateTime(new Date());
        familyVo.setCoverImgUrl("kklll");
        redisCache.set("familyVo_11",familyVo,2,TimeUnit.MINUTES);
        String string = stringRedisCache.get("familyVo_11");
        System.out.println("999999:"+string);
        //打印结果：999999:["com.lv.zupu.entity.FamilyVo",{"familyName":"吕世春","id":null,"createTime":["java.util.Date",1541057545959],"createUserId":null,"updateTime":null,"coverImgUrl":"kklll","describe":null,"authorName":null,"isDelete":null}]
        //不能直接转成FamilyBo
        return familyVo;
    }

    public FamilyVo redisMapTest(){
        return null;
    }
}
