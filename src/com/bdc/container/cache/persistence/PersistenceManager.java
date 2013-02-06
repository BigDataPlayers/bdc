package com.bdc.container.cache.persistence;



import com.bdc.container.cache.Key;
import com.bdc.container.cache.Value;

import java.util.Iterator;

public interface PersistenceManager {

    public void put(Key key, Value value);
    public Iterator iterator();

}
