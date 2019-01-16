package com.lv.zupu;

import com.baomidou.mybatisplus.toolkit.MapUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lv.zupu.entity.FamilyVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2019/1/8 19:12
 * @Version: 1.0
 * modified by:
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class RedissonTest {
    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void test1(){
        System.out.println(redissonClient);
        RMap<String,Object> rMap = redissonClient.getMap("My_map");
        rMap.put("id",1111);
        rMap.put("name","吕荣生");
        rMap.put("address","湖南长沙");
        rMap.expire(20, TimeUnit.SECONDS);
    }

    public void testGeo(){
        RGeo rGeo = redissonClient.getGeo("my_geo");
        redissonClient.getLock("my_lock");
    }

    /**
     * 对单个对象操作 相当于操作string类型
     */
    @Test
    public void testrBucket(){
        RBucket rBucket =redissonClient.getBucket("my_bucket");
        FamilyVo familyVo = new FamilyVo();
        familyVo.setFamilyName("周氏");
        familyVo.setAuthorName("吕增");
        familyVo.setDescribe("好不好我说了算:");
        familyVo.setId(1L);
        familyVo.setCreateTime(new Date());
        rBucket.set(familyVo);
    }

    /**
     * 异步获取值并删除
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testBucketDel() throws ExecutionException, InterruptedException {
        RBucket<FamilyVo> rBucket =redissonClient.getBucket("my_bucket");
        //异步获取并且删除
        RFuture<FamilyVo> rFuture = rBucket.getAndDeleteAsync();
        FamilyVo familyVo = rFuture.get();
        System.out.println(familyVo);
    }

    /**
     * 使用lua脚本操作redis
     * 这里需要注意点两个key不在同一个slot是插入不了的 获取多个key如果不在一个slot也不行
     * 插入的使用{}来限定计算slot 源码注释通过VALUES来获取参数值是有问题的 ，应该用ARGV来获取参数的值
     */
    @Test
    public void testLua(){
        String my_lua1 = "{my_}lua1";
        String lua1 = "lua1";
        String my_lua2 = "{my_}lua2";
        String lua2 = "lua2";
        List<Object> keys = Lists.newArrayList();
        keys.add(my_lua1);
        keys.add(my_lua2);
      /*  List<String> values = Lists.newArrayList();
        values.add(lua1);
        values.add(lua2);*/

        String script = "redis.call('set',KEYS[1],ARGV[1]); "
                +"redis.call('set',KEYS[2],ARGV[2]); "
                +"local valu1 = redis.call('get',KEYS[1]); "
                +"return valu1";
        RScript rScript = redissonClient.getScript();
       Object value = rScript.eval(RScript.Mode.READ_WRITE, script, RScript.ReturnType.VALUE,keys,lua1,lua2);
        System.out.println(value);
    }
}
