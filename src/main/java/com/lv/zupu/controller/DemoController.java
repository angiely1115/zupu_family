package com.lv.zupu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lv.zupu.base.aspect.MyLog;
import com.lv.zupu.domain.FamilyBo;
import com.lv.zupu.entity.FamilyVo;
import com.lv.zupu.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2018/7/11 16:21
 * @Version: 1.0
 * modified by:
 */
@RestController
@RequestMapping("demo")
public class DemoController {
    @Autowired
    private DemoService demoService;

    @GetMapping("redis_test")
    public void redisTest(){
        demoService.redisTest();
    }
    @RequestMapping("/valid")
    @ResponseBody
    public String validDemo(@Valid @RequestBody FamilyVo familyVo) throws JsonProcessingException {
        familyVo.setFamilyName("段氏");
        familyVo.setCreateTime(new Date());
        return demoService.demoJson(familyVo);
    }

    @MyLog("请求logdemo")
    @RequestMapping("/mylog")
    public FamilyBo myLogDemo(String name){
        FamilyBo familyBo = new FamilyBo();
        familyBo.setAuthorName(name);
        return familyBo;
    }
    @RequestMapping("/thpoll")
    public String threadPollDemo(String name){
        demoService.aysnThreadPool();
        return Thread.currentThread().getName()+"poll:"+name;
    }

    //@RequestBody注解常用来处理content-type不是默认的application/x-www-form-urlcoded编码的内容 否则会报media错误
    @RequestMapping("/requestBodyTest")
    @ResponseBody
    public String requestBodyTest(@RequestBody FamilyBo familyVo) throws JsonProcessingException {
       return familyVo.toString();
    }
}