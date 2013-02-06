package com.bdc.container.test;

import com.bdc.container.bootstrap.Server;
import com.bdc.container.bootstrap.Service;
import com.bdc.container.cache.Cache;
import com.bdc.container.cache.CacheList;
import com.bdc.container.cache.Key;
import com.bdc.container.cache.Value;
import org.springframework.context.ApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: mkhanwalker
 * Date: 10/11/12
 * Time: 2:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorld implements Service {

        String message;
        String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public void init() {
       System.out.println(message);

        ApplicationContext context = Server.getInstance().getContext();
        CacheList caches = (CacheList)context.getBean("CacheList");
        Cache cache = caches.get("Cache2");
       // cache.setManager(new FilePersistenceManager("c:/temp/trial.dat"));
        cache.recover();
        cache.printContents();
        Key k = new KeyImpl("Hello");
        Value v = new ValueImpl("World");
        cache.put(k,v);
        System.out.println(cache.get(k));
    }

    @Override
    public void start() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void stop() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void pause() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
