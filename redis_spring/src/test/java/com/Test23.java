package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baowei.entity.User;

public class Test23 {

	@SuppressWarnings({ "resource", "unused" })
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext2.xml");
    	RedisClusterClient redisClusterClient=(RedisClusterClient) applicationContext.getBean("redisClusterClient");
    	
    	final String SYS_USER_TABLE_SEX_FEMAN = "SYSUSERTABLESEXFEMAN";
    	final String SYS_USER_TABLE_SEX_MAN = "SYSUSERTABLESEXMAN";
    	final String SYS_USER_TABLE_AGE_24 = "SYSUSERTABLEAGE24";
    	
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

		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < users.size(); i++) {
			map.put(i+"",users.get(i));
			
			if ("man".equals(users.get(i).getSex())) {
				redisClusterClient.sAdd(SYS_USER_TABLE_SEX_MAN, i+"");
			}
			if ("24".equals(users.get(i).getAge() + "")) {
				redisClusterClient.sAdd(SYS_USER_TABLE_AGE_24, i+"");
			}
		}		
		redisClusterClient.hMSet("USERTABLES", map);	
		
		//List<Object> list=redisClusterClient.sInter(SYS_USER_TABLE_SEX_MAN.getBytes(),SYS_USER_TABLE_AGE_24.getBytes());
		
		List<byte[]> blist=new ArrayList<byte[]>();
		blist.add(SYS_USER_TABLE_SEX_MAN.getBytes());
		blist.add(SYS_USER_TABLE_AGE_24.getBytes());
		
		byte[][] bb=new byte[blist.size()][];
		for (int i = 0; i < bb.length; i++) {
			bb[i]=blist.get(i);
		}
		
		
		
		
		Set<byte[]> set=redisClusterClient.sInterByte(bb);
		
		Iterator<byte[]> it=set.iterator();
		byte[][] bb2=new byte[set.size()][];
		for (int i = 0;it.hasNext() ; i++) {
			bb2[i]=it.next();
		}
		
		
		
		List<Object> objects=redisClusterClient.hMGet("USERTABLES", bb2);
		for (Object object : objects) {
			User u=(User) object;
			System.out.println(u);
		}

    	

	}

}
