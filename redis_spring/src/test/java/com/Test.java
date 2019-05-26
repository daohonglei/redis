package com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baowei.entity.User;
import com.baowei.json.util.GsonUtil;

import redis.clients.jedis.JedisCluster;

public class Test {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
       /* ApplicationContext ac =  new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        final UserDao userDAO = (UserDao)ac.getBean("userDao");
        User user1 = new User();
        user1.setId(1);
        user1.setName("obama");
        userDAO.saveUser(user1);*/
        //User user2 = userDAO.getUser(1);
        //System.out.println(user2.getName());
       /* new Thread(new Runnable() {
			public void run() {
				userDAO.getUser(1);
			}
		}).start();*/
		
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext_redis.xml");
        JedisCluster jedisCluster =  (JedisCluster) applicationContext.getBean("redisClient");
        /*jedisCluster.setnx("name", "456");
        jedisCluster.expire("name", 2);
        String string = jedisCluster.get("name");
        System.out.println(string);
        jedisCluster.close();*/
         
       
        /*jedisCluster.hset("user","name","ldh2");
        
        
        Map<String, String> map=new HashMap<String, String>();
        map.put("name", "ldh3");
        map.put("age", "18");
        map.put("pwd", "123");
		jedisCluster.hmset("user", map);*/
        
        
        final String SysUserTable = "SYSUSERTABLE";
    	
    	
    	List<User> users = new ArrayList<User>();
		for (int i = 0; i < 5; i++) {
			User user = new User();
			user.setId(i+"");
			user.setName("zhang" + i);
			user.setSex("man");
			user.setAge(20 + i);
			users.add(user);
		}
		for (int i = 5; i < 10; i++) {
			User user = new User();
			user.setId(i+"");
			user.setName("zhang" + i);
			user.setSex("feman");
			user.setAge(20 + i);
			users.add(user);
		}

		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < users.size(); i++) {
			map.put(users.get(i).getId() + "",GsonUtil.object2Json(users.get(i)));
		}
		
		System.out.println(map);
		
		
		jedisCluster.hmset(SysUserTable, map);

    }
}
