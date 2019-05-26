package com;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

public class UserDaoImpl implements UserDao {

    @Autowired
    protected RedisTemplate<Serializable, Serializable> redisTemplate;

    public void saveUser(final User user) {
        redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.hSet(redisTemplate.getStringSerializer().serialize("Users"),
                		redisTemplate.getStringSerializer().serialize("user.uid." + user.getId()),
                	    redisTemplate.getStringSerializer().serialize(user.getName()));
                return null;
            }
        });
    }
    
    

   /* public User getUser(final long id) {
        return redisTemplate.execute(new RedisCallback<User>() {
            public User doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = redisTemplate.getStringSerializer().serialize("user.uid." + id);
                if (connection.exists(key)) {
                    byte[] value = connection.get(key);
                    String name = redisTemplate.getStringSerializer().deserialize(value);
                    User user = new User();
                    user.setName(name);
                    user.setId(id);
                    return user;
                }else{
                	System.out.println(2333333);
                	if(connection.setNX(redisTemplate.getStringSerializer().serialize("lock"),
             			   			 redisTemplate.getStringSerializer().serialize("lock"))){
                		connection.expire(redisTemplate.getStringSerializer().serialize("lock"), 3*60);
                    	User user1 = new User();
                        user1.setId(1);
                        user1.setName("obama");
                        saveUser(user1);
                        connection.del(redisTemplate.getStringSerializer().serialize("lock"));  
                	}else{
                		try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
                	}
                }
                getUser(id);
                return null;
            }
        });
    }*/
    
    
    public User getUser(final long id) {
        return redisTemplate.execute(new RedisCallback<User>() {
            public User doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = redisTemplate.getStringSerializer().serialize("user.uid." + id);
                if (connection.exists(key)) {
                    byte[] value = connection.get(key);
                    String name = redisTemplate.getStringSerializer().deserialize(value);
                    User user = new User();
                    user.setName(name);
                    user.setId(id);
                    return user;
                }else{
                	synchronized (UserDaoImpl.class) {
                		System.out.println(233);
                		User user1 = new User();
                        user1.setId(1);
                        user1.setName("obama");
                        saveUser(user1);
                        return user1;
					}
                }
            }
        });
    }

}
