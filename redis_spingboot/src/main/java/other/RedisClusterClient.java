package other;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class RedisClusterClient {

	@Autowired
   private RedisTemplate<String,String> redisTemplate;
 
   //添加数据
   public void put(Object key,Object value) {
        if(null == value) {
            return;
        }
 
        if(value instanceof String) {
            if(StringUtils.isEmpty(value.toString())) {
                return;
            }
        }
 
        //TODO Auto-generated method stub
        final String keyf = key + "";
        final Object valuef = value;
        final long liveTime = 86400;
 
        redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyb = keyf.getBytes();
                byte[] valueb = toByteArray(valuef);
                connection.set(keyb, valueb);
                
                
                if (liveTime > 0) {
                    connection.expire(keyb, liveTime);
                }
                connection.close();
                return 1L;
            }
        });
   }
   
   public void hSet(final Object key,final Object field, final Object value) {
	   redisTemplate.execute(new RedisCallback<Long>() {
           public Long doInRedis(RedisConnection connection) throws DataAccessException {
               byte[] keyb = key.toString().getBytes();
               byte[] valueb = toByteArray(value);
               byte[] fieldb = toByteArray(field);
               connection.hSet(keyb, fieldb, valueb);
               connection.close();
               return 1L;
           }
       });
   }
   
   public void hMSet(final Object key,final Map<String,Object> map){
	   final Map<byte[],byte[]> bMap=new HashMap<byte[], byte[]>();
	   Set<String> set=map.keySet();
	   for (String string : set) {
		  bMap.put(toByteArray(string), toByteArray(map.get(string)));
	   }
	   redisTemplate.execute(new RedisCallback<Long>() {
          public Long doInRedis(RedisConnection connection) throws DataAccessException {
              byte[] keyb =key.toString().getBytes();
              connection.hMSet(keyb,bMap);
              connection.close();
              return 1L;
          }
      });
   }
   
   public Object hGet(final Object key,final Object field){
	   Object object;
	   object=redisTemplate.execute(new RedisCallback<Object>() {
          public Object doInRedis(RedisConnection connection) throws DataAccessException {
              byte[] keyb = key.toString().getBytes();
              byte[] fieldb = toByteArray(field);
              byte[] value=connection.hGet(keyb, fieldb);
              connection.close();
              return toObject(value);
          }
      });
	   return object;
   } 
   
   public  List<Object> hMGet(final Object key,final byte[]... keys){
	   List<Object> list;
	   list=redisTemplate.execute(new RedisCallback<List<Object>>() {
          public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
              byte[] keyb = key.toString().getBytes();
              List<byte[]> value=connection.hMGet(keyb, keys);
              connection.close();
              List<Object> list=new ArrayList<Object>();
              for (byte[] bs : value) {
            	  list.add(toObject(bs));
			  }
              return list;
          }
      });
	   return list;
   } 
   
   public void sAdd(final Object key,final Object value){
	   redisTemplate.execute(new RedisCallback<Long>() {
          public Long doInRedis(RedisConnection connection) throws DataAccessException {
              byte[] keyb = key.toString().getBytes();
              byte[] valueb = toByteArray(value);
              connection.sAdd(keyb, valueb);
              connection.close();
              return 1l;
          }
      });
   } 
   
   
   public List<Object> sInter(final byte[]... keys){
	   List<Object> list;
	   list=redisTemplate.execute(new RedisCallback<List<Object>>() {
          public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
              List<Object> list=new ArrayList<Object>();
              Set<byte[]> set=connection.sInter(keys);
              connection.close();
              for (byte[] bs : set) {
            	  list.add(toObject(bs));
			  }
              return list;
          }
       });
	   return list;
   }
   
   public Set<byte[]> sInterByte(final byte[]... keys){
	   Set<byte[]> set;
	   set=redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
          public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
              Set<byte[]> set=connection.sInter(keys);
              connection.close();
              return set;
          }
       });
	   return set;
   } 

     // 获取数据
    public Object get(Object key) {
        final String keyf = (String) key;
        Object object;
        object = redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = keyf.getBytes();
                byte[] value = connection.get(key);
                if (value == null) {
                    return null;
                }
                connection.close();
                return toObject(value);
            }
        });
        return object;
    }
 
    /**
     * 描述 : <byte[]转Object>. <br>
     * <p>
     * <使用方法说明>
     * </p>
     *
     * @param bytes
     * @return
     */
    private Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }
 
    private byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }
}
