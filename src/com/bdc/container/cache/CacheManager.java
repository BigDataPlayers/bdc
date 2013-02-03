package com.bdc.container.cache;

import com.bdc.container.bootstrap.Server;
import com.bdc.container.test.KeyImpl;

/**
 * Created with IntelliJ IDEA.
 * User: a09726a
 * Date: 1/4/13
 * Time: 9:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class CacheManager {

    public static final String CACHE_LIST = "CacheList";

    private static Cache getCache(String cacheName) {
        CacheList caches = (CacheList) Server.getInstance().getContext().getBean(CacheManager.CACHE_LIST);
        return caches.get(cacheName);

    }
    public static Value get(String cacheName, String keyVal) {
        return getCache(cacheName).get(new KeyImpl(keyVal));
    }

    public static void put(String cacheName, String keyVal, Value value) {
        getCache(cacheName).put(new KeyImpl(keyVal), value);
    }
}
