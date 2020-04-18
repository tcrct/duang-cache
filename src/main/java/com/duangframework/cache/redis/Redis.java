package com.duangframework.cache.redis;

import com.duangframework.cache.core.CacheException;
import com.duangframework.cache.core.CacheModel;
import com.duangframework.cache.redis.serializer.ISerializer;
import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.SafeEncoder;

public class Redis {

    private static final Logger LOG = LoggerFactory.getLogger(Redis.class);

    private String name;
    private JedisPool jedisPool;
    private ISerializer serializer;
    private IKeyNamingPolicy keyNamingPolicy;
    private static Redis REDIS;

    public Redis(String name, JedisPool jedisPool, ISerializer serializer, IKeyNamingPolicy keyNamingPolicy) {
        this.name = name;
        this.jedisPool = jedisPool;
        this.serializer = serializer;
        this.keyNamingPolicy = keyNamingPolicy;
        REDIS = this;
    }

    public static Redis getInstance() {
        return REDIS;
    }

    private Jedis getResource()  {
        try {
            if(null == jedisPool) {
                throw new CacheException("jedisPool is null");
            }
            return jedisPool.getResource();
        } catch (Exception e) {
            throw new CacheException("取jedis资料时出错: " + e.getMessage(), e);
        }
    }

    private void close(Jedis jedis) {
        if ( jedis != null) {
            jedis.close();
//            jedisPool.close();
        }
    }

    /**
     * 调用缓存方法
     * @param action
     * @param <T>
     * @return
     */
    public <T> T call(JedisAction action) {
        T result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = (T) action.execute(jedis);
        } catch (Exception e) {
            LOG.warn(e.getMessage(), e);
        }
        finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 序列化key
     * @param key 要缓存的key值
     * @return
     */
    protected byte[] serializerKey(Object key) {
        return serializer.keyToBytes(String.valueOf(key));
    }

    /**
     * 序列化value
     * @param value 要缓存的value值
     * @return
     */
    private byte[] serializeValue(Object value) {
        return serializer.valueToBytes(value);
    }

    protected Object deSerializeValue(byte[] bytes) {
        return serializer.valueFromBytes(bytes);
    }

    /*************************** Redis里的方法 ************************/

    /**
     * 存放 key value 对到 redis
     * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
     * 对于某个原本带有生存时间（TTL）的键来说， 当 SET 命令成功在这个键上执行时， 这个键原有的 TTL 将被清除。
     */
    public boolean set(CacheModel model) {
        return call(new JedisAction<Boolean>(){
            @Override
            public Boolean execute(Jedis jedis) {
                String result = jedis.set(serializerKey(model.getKey()), serializeValue(model.getValue()));
                boolean isOk =  "OK".equalsIgnoreCase(result);
                if(isOk) {
                    expire(model);
                }
                return isOk;
            }
        });
    }

    /**
     * 返回 key 所关联的 value 值
     * 如果 key 不存在那么返回特殊值 nil 。
     */
    @SuppressWarnings("unchecked")
    public <T> T get(CacheModel model) {
        return call(new JedisAction<T>(){
            @Override
            public T execute(Jedis jedis) {
                return (T)deSerializeValue(jedis.get(serializerKey(model.getKey())));
            }
        });
    }

    /**
     * 根据key设置过期时间
     * @param model  CacheModel对象
     * @return
     *  1 如果成功设置过期时间。
     * 0  如果key不存在或者不能设置过期时间。
     */
    public Long expire(final CacheModel model) {
        return call(new JedisAction<Long>(){
            @Override
            public Long execute(Jedis jedis) {
                if(model.getKeyTTL() > 0) {
                    return jedis.expire(model.getKey(), model.getKeyTTL());
                }
                return 0L;
            }
        });
    }

    /**
     * 根据key删除指定的内容
     * 如果key值不存在，则忽略
     * @param model
     * @return
     */
    public Long del(final CacheModel model){
        return call(new JedisAction<Long>(){
            @Override
            public Long execute(Jedis jedis) {
                return jedis.del(model.getKey());
            }
        });
    }
}
