package com.example.demo;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClusterTest {
	
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    private ValueOperations<String, String> stringRedis;
    
   // private HashOperations<String,String,String> hashOperations;
    
    @PostConstruct
    public void init(){
        stringRedis=redisTemplate.opsForValue();
        
		/*
		 * hashOperations=redisTemplate.opsForHash();
		 * 
		 * hashOperations.put("233", "233", "233");
		 */
    }
    
    
    @Test
    public void testString (){
        stringRedis.set("name", "丁洁2");
        System.out.println(stringRedis.get("name"));
    }
}