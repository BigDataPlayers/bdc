package com.bdc.container.cache.persistence;



import com.bdc.container.cache.Key;
import com.bdc.container.cache.Value;

import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 12/12/12
 * Time: 4:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class FilePersistenceManagerIterator implements  Iterator {

    RandomAccessFile file;
    ObjectInputStream is;
    public FilePersistenceManagerIterator(ObjectInputStream is)
    {
       this.is = is;
    }

    Entry entry;
    @Override
    public boolean hasNext() {
        if (is==null)
            return false;

        Key k =null;
        Value v = null;
        try {
             k = (Key)is.readObject()  ;
            v = (Value)is.readObject();
        } catch (Exception e) {
            entry = null ;
            return false;
        }
        entry = new Entry();
        entry.setKey(k);
        entry.setValue(v);
        return true;
    }

    @Override
    public Object next() {
      return entry;
    }

    @Override
    public void remove() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
