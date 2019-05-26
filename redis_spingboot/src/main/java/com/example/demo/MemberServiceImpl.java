package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.util.RedisUtil;

import other.RedisClusterClient;

@RestController
public class MemberServiceImpl {

	
	 @Autowired
	 private RedisUtil redisUtil;
    
    @GetMapping("/testRedisSet")
    public String testRedisSet(String key, String value) {
		 redisUtil.set("test", "test"); 
		 System.out.println(redisUtil.get("test"));
         return redisUtil.get("test");
    }
}
