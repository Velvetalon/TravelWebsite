package cn.itcast.travel.cache.impl;

import cn.itcast.travel.cache.CategoryCache;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;

public class CategoryCacheImpl implements CategoryCache {
    private static final String CATEGORY_CACHE = "Category";

    @Override
    public List<Category> getCategoryCache(){
        Jedis jedis = JedisUtil.getJedis();
        String cache = jedis.get(CATEGORY_CACHE);
        if (cache != null && !cache.isEmpty()) {
            try {
                jedis.close();
                return new ObjectMapper().readValue(cache, List.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        jedis.close();
        return null;
    }

    @Override
    public void setCategoryCache( List<Category> list ){
        Jedis jedis = JedisUtil.getJedis();
        try {
            //保存缓存
            jedis.set(CATEGORY_CACHE, new ObjectMapper().writeValueAsString(list));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeCategoryCache(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.del(CATEGORY_CACHE);
    }

}
