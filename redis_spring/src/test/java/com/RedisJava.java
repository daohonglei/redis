package com;

import java.util.concurrent.locks.ReentrantLock;

import redis.clients.jedis.Jedis;

public class RedisJava {
	
	private ReentrantLock reentrantLock=new ReentrantLock();
	private String age;
	
	public static void main(String[] args) {
       final RedisJava redisJava=new RedisJava();
       
      
        
        //System.out.println(redisJava.get2("age"));
      
       
       new Thread(new Runnable() {
			public void run() {
				System.out.println(redisJava.get2("age"));
			}
		}).start();
       
      
       new Thread(new Runnable() {
			public void run() {
				System.out.println(redisJava.get2("age"));
			}
		}).start();
      
    /*  try {
		 Thread.sleep(5000);
	  } catch (InterruptedException e) {
		 e.printStackTrace();
	  }
      
      System.out.println(redisJava.get2("age"));*/
       
    }
	
	/*public void get(String key){
		Jedis jedis=RedisUtil.getJedis();
		String age=jedis.get(key);
		System.out.println(100000);
        if(age==null){
        	if(jedis.setnx("name", "1") != null){
        		try {
        			//jedis.expire("name", 4 * 1000); 
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }else{
				System.out.println(12345);
				get(key);
            }
        }
        System.out.println(age);
	}*/
	
	public String get2(String key){
		Jedis jedis=RedisUtil.getJedis();
		age=jedis.get(key);
		System.out.println(age+"---------------------------");
        if(age==null){
			try{
				reentrantLock.lock();
				Thread.sleep(10000);
				System.out.println(1234567890);
				jedis.set(key, "18");
				get2(key);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}finally{
				reentrantLock.unlock();
			}
        }
        return age;
	}
}
