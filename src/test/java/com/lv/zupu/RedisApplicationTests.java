package com.lv.zupu;

import com.lv.zupu.base.redis.StringRedisCache;
import com.lv.zupu.entity.FamilyVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Author: lvrongzhuan
 * @Description: redis 不同数据结构测试 领悟 对于一些业务的操作 可以优先考虑用redis来存储在考虑mysql
 * @Date: 2018/11/8 10:26
 * @Version: 1.0
 * modified by:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {
    @Autowired
    private StringRedisCache stringRedisCache;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testDemo01() {
        long l = stringRedisCache.leftPusList("back_order_no_180", new String[]{"太好了，下次在来", "太好了，下次再来", "差评，物流慢"});
        stringRedisTemplate.opsForList().leftPush("back_order_no_180", "最新一条评论");
        stringRedisCache.leftPusList("back_order_no_180", "更新最新一条评论");
        System.out.println("back_order_no_180有多少条数据：" + l);
        List<String> strings = stringRedisCache.listRange("back_order_no_180", 0, -1);
        System.out.println("查询back_order_no_180所有的数据:" + strings);
        String s = stringRedisCache.leftPop("back_order_no_180");
        System.out.println(s);
    }

    @Test
    public void testRange() {
        List<String> strings = stringRedisCache.listRange("back_order_no_180", 0, -1);
        //会查询条数据 分页从0开始
        System.out.println("查询back_order_no_180的数据:" + strings);
    }

    @Test
    public void testListRemove() {
        /**
         * count 为0 删除全部的 不为0删除指定删除几个
         */
        stringRedisCache.listRemove("back_order_no_180", 2, "太好了，下次再来");
        List<String> strings = stringRedisCache.listRange("back_order_no_180", 0, -1);
        System.out.println("查询back_order_no_180所有的数据:" + strings);
    }

    @Test
    public void testListObject() {
        for (int i = 0; i < 9; i++) {
            FamilyVo familyVo = new FamilyVo();
            familyVo.setFamilyName("周氏");
            familyVo.setAuthorName("吕增");
            familyVo.setDescribe("好不好我说了算:" + i);
            familyVo.setId(1L);
            familyVo.setCreateTime(new Date());
            Long l = redisTemplate.opsForList().leftPush("back_order_no_199", familyVo);
            System.out.println("back_order_no_199 有" + l + "条数据");
        }
        List<Object> familyVos = redisTemplate.opsForList().range("back_order_no_199", 0, -1);
        System.out.println("获取back_order_no_199 所有数据：" + familyVos);
        FamilyVo familyVo1 = (FamilyVo) redisTemplate.opsForList().leftPop("back_order_no_199");
        System.out.println("redis list 获取第一个（最后一条插入的）对象值：" + familyVo1);
    }

    @Test
    public void listRemoveObject() {
        List<Object> familyVoList = redisTemplate.opsForList().range("back_order_no_199", 0, 0);
        long l = redisTemplate.opsForList().remove("back_order_no_199", 1, familyVoList.get(0));
        System.out.println("删除的个数：" + l);
        List<Object> familyVos = redisTemplate.opsForList().range("back_order_no_199", 0, -1);
        System.out.println("获取back_order_no_199 所有数据：" + familyVos);
    }

    /**
     * 带排序的set
     */
    @Test
    public void testZsetAdd() {
        redisTemplate.opsForZSet().add("integral_list", 1, 100);
        redisTemplate.opsForZSet().add("integral_list", 2, 7);
        redisTemplate.opsForZSet().add("integral_list", 3, 66);
        redisTemplate.opsForZSet().add("integral_list", 4, 89);
        redisTemplate.opsForZSet().add("integral_list", 7, 63);
        redisTemplate.opsForZSet().add("integral_list", 8, 130);
    }

    @Test
    public void testZsetOption() {
        long count = redisTemplate.opsForZSet().count("integral_list", 100, 130);
        System.out.println("统计某个分数段元素的个数:" + count);
        //元素按照积分从小到大排序取出
        Set<Object> objects = redisTemplate.opsForZSet().range("integral_list", 0, -1);
        System.out.println("取出zSet某个集合所有元素：" + objects);
        objects = redisTemplate.opsForZSet().reverseRange("integral_list", 0, -1);
        //元素按照积分从大到小排序取出
        System.out.println("取出zSet某个集合所有分数从大到下元素：" + objects);
        //取出分数在某个区间的元素
        objects = redisTemplate.opsForZSet().rangeByScore("integral_list", 10, 100);
        System.out.println("取出zSet某个集合分数范围在某个区间从小到大元素：" + objects);
        // 取出分数在某个区间的元素和分数
        Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisTemplate.opsForZSet().rangeByScoreWithScores("integral_list", 10, 100);
        System.out.println("取出zSet某个集合分数范围在某个区间从小到大元素和分数：" + typedTuples);
        objects = redisTemplate.opsForZSet().rangeByLex("integral_list", new RedisZSetCommands.Range());
        System.out.println("取出zSet某个集合所有分数范围从小到大元素 rangeByLex：" + objects);
        //确定某个元素的索引位置
        redisTemplate.opsForZSet().rank("integral_list",1);
    }

    @Test
    public void zSetRemove() {
        //根据元素值删除元素
        redisTemplate.opsForZSet().remove("integral_list", 1);
        System.out.println(redisTemplate.opsForZSet().reverseRange("integral_list", 0, -1));
        //删除元素 根据起始位置来
        redisTemplate.opsForZSet().removeRange("integral_list", 0, 2);
        System.out.println(redisTemplate.opsForZSet().reverseRange("integral_list", 0, -1));
        //删除元素 根据分数值的范围
        redisTemplate.opsForZSet().removeRangeByScore("integral_list", 66, 90);
        System.out.println(redisTemplate.opsForZSet().reverseRange("integral_list", 0, -1));
    }

    @Test
    public void testHashAdd() {
        redisTemplate.opsForHash().put("product_id:1", "id", "1");
        redisTemplate.opsForHash().put("product_id:1", "name", "指向檀木");
        redisTemplate.opsForHash().put("product_id:1", "age", "200");
        redisTemplate.opsForHash().put("product_id:2", "id", "2");
        redisTemplate.opsForHash().put("product_id:2", "name", "樟树");
        redisTemplate.opsForHash().put("product_id:2", "age", "300");
    }

    @Test
    public void testHashGet(){
        //获取键为product_id:1 hash的键为name 的值
        Object object = redisTemplate.opsForHash().get("product_id:1","name");
        System.out.println("product_id:1:key-name:"+object);
    }
}
