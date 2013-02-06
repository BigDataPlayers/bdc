package com.bdc.container.cache;



import com.bdc.container.cache.persistence.Entry;
import com.bdc.container.cache.persistence.PersistenceManager;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 12/11/12
 * Time: 4:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Cache {

    ConcurrentMap<Key,Value> data = new ConcurrentHashMap<Key,Value>();

    PersistenceManager manager=null;

    public PersistenceManager getManager() {
        return manager;
    }

    public void setManager(PersistenceManager manager) {
        this.manager = manager;
    }

    public Cache()
    {

    }

    public void printContents()
    {
        System.out.println(data);
    }

    String name ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void put(Key key , Value value)
    {
       if (manager!=null)
       {
           manager.put(key,value);
       }
       data.put(key,value) ;
    }

    public Value get(Key key)
    {
        return data.get(key);
    }

    public void recover()
    {
        if (manager!=null)
        {
            Iterator iterator = manager.iterator();
            while(iterator.hasNext())
            {
                Entry e = (Entry)iterator.next();
                data.put(e.getKey(),e.getValue());
            }
        }
    }
}
