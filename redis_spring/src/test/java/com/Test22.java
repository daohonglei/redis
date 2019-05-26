package com;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test22 {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext2.xml");
    	RedisClusterClient redisClusterClient=(RedisClusterClient) applicationContext.getBean("redisClusterClient");
    	
    	redisClusterClient.hSet("user","name","ldh");
    	redisClusterClient.hSet("user","age","18");
    	//System.out.println(redisClusterClient.get("user").toString());
    	
    	Map<String, Object> map=new HashMap<String, Object>();
    	
    	for (int i = 0; i <10; i++) {
    		 map.put(i+"", new User(i, "name"+i));
		}
    	
      
    	
    	redisClusterClient.hMSet("USERTABLES", map);
    	User user=(User) redisClusterClient.hGet("USERTABLES", "1");
    	System.out.println(user.getId());
    	
    	//System.out.println(redisClusterClient.get("user").toString());
    	
    	
    	
    	/*redisClusterClient.putHash("name", "ldh");
    	redisClusterClient.putHash("age", "18");
    	redisClusterClient.putHash("pwd", "pwd");
    	
    	
    	System.out.println(redisClusterClient.hGet("name").toString());*/
    	
    	/*Map<byte[], byte[]> map=new HashMap<byte[], byte[]>();
    	map.put("1".getBytes(), new User(1L,"ldh").toString().getBytes());
    	redisClusterClient.putHash(map);*/
    	

	}

}
