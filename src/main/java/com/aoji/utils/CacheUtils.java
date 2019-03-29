package com.aoji.utils;

import com.aoji.model.CacheItem;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class CacheUtils {
    private static Map<String,CacheItem> cache = new ConcurrentHashMap<String, CacheItem>();
    public static void setCache(String key, Object data,Integer expireMinute){
        CacheItem cacheItem = new CacheItem();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MINUTE, expireMinute);
        cacheItem.setExpireDate(c.getTime());
        cacheItem.setData(data);
        cache.put(key,cacheItem);
    }
    public static CacheItem getCache(String key){
        CacheItem cacheItem = cache.get(key);
        if(cacheItem != null){
            if(cacheItem.expireDate.getTime() > System.currentTimeMillis()){
                return cacheItem;
            }else{
                cache.remove(key);
            }
        }
        return null;
    }
    public static void removeCache(String key){
        cache.remove(key);
    }
}
