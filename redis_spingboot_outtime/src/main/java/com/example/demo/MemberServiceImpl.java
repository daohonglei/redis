package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberServiceImpl {

    @Autowired
    private BaseRedisService baseRedisService;

    @GetMapping("/testRedisSet")
    public String testRedisSet(String key, String value) {
        baseRedisService.setString("age","180");
        System.out.println(baseRedisService.getString("age"));
        return baseRedisService.getString("age");
    }
}
