package com.nowcoder.community;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class RedisTests {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void testString(){
        String redisKey = "test:count";
        redisTemplate.opsForValue().set(redisKey, 1);
        System.out.println(redisTemplate.opsForValue().get(redisKey));
        System.out.println(redisTemplate.opsForValue().decrement(redisKey));
        System.out.println(redisTemplate.opsForValue().increment(redisKey));
    }

    @Test
    public void testHashes(){
        String redisKey = "test:user";
        redisTemplate.opsForHash().put(redisKey, "id", 1);
        redisTemplate.opsForHash().put(redisKey, "name", "zhangsan");
        System.out.println(redisTemplate.opsForHash().get(redisKey, "id"));
        System.out.println(redisTemplate.opsForHash().get(redisKey, "name"));
    }

    @Test
    public void testList(){
        String redisKey = "test:ids";
        redisTemplate.opsForList().leftPush(redisKey, 101);
        redisTemplate.opsForList().leftPush(redisKey, 102);
        redisTemplate.opsForList().leftPush(redisKey, 103);
        System.out.println( redisTemplate.opsForList().size(redisKey));
        System.out.println(redisTemplate.opsForList().index(redisKey, 0));
        System.out.println(redisTemplate.opsForList().range(redisKey, 0, 2));

        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
    }


    @Test
    public void testSet(){
        String redisKey = "test:teachers";
        redisTemplate.opsForSet().add(redisKey, "刘备", "关羽", "张飞");
        System.out.println( redisTemplate.opsForSet().size(redisKey));
        System.out.println(redisTemplate.opsForSet().pop(redisKey));
        System.out.println(redisTemplate.opsForSet().members(redisKey));
    }


    @Test
    public void testOrderSet(){
        String redisKey = "test:students";
        redisTemplate.opsForZSet().add(redisKey, "刘备", 10);
        redisTemplate.opsForZSet().add(redisKey,"关羽", 20);
        redisTemplate.opsForZSet().add(redisKey,"张飞", 60);
        redisTemplate.opsForZSet().add(redisKey,"赵云", 30);

        System.out.println(redisTemplate.opsForZSet().zCard(redisKey));
        System.out.println(redisTemplate.opsForZSet().score(redisKey, "八戒"));
        System.out.println(redisTemplate.opsForZSet().rank(redisKey, "关羽"));
        System.out.println(redisTemplate.opsForZSet().reverseRank(redisKey, "关羽"));
        System.out.println(redisTemplate.opsForZSet().range(redisKey, 0, 2));
        System.out.println(redisTemplate.opsForZSet().reverseRange(redisKey, 0, 2));
    }

    @Test
    public void testKeys(){
        redisTemplate.delete("test:user");
        Boolean aBoolean = redisTemplate.hasKey("test:user");
        System.out.println(aBoolean);
        redisTemplate.expire("test:students", 10, TimeUnit.SECONDS);
    }

    //多次访问同一个key,通过绑定的方式避免每次都传入key
    @Test
    public void testBoundOperations(){
        String redisKey = "test:count";
        BoundValueOperations operations = redisTemplate.boundValueOps(redisKey);
        operations.increment();
        operations.increment();
        operations.increment();
        operations.increment();
        System.out.println(operations.get());
    }

    //编程式事务
    @Test
    public void testTransactional(){
        Object obj = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String redisKey = "test:tx";
                operations.multi();  // 启用事务
                operations.opsForSet().add(redisKey, "zhangsan");
                operations.opsForSet().add(redisKey, "lisi");
                operations.opsForSet().add(redisKey, "wangwu");
                return operations.exec(); //提交事务
            }
        });
        System.out.println(obj);
    }



}
