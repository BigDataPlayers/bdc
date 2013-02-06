package com.bdc.container.cache;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 12/11/12
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class CacheList {

    Map<String, Cache> cacheMap = new HashMap<String, Cache>();

    List<Cache> caches;
    public void setCaches(List<Cache> caches) {

        for (Cache cache : caches)
        {
            cacheMap.put(cache.getName(),cache);
        }
    }

    public Cache get(String name)
    {
        return cacheMap.get(name);
    }
}
