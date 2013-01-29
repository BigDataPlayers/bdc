package com.bdc.container.cache.persistence;



import com.bdc.container.cache.Key;
import com.bdc.container.cache.Value;

import java.io.*;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 12/12/12
 * Time: 3:59 PM
 * To change this template use File | Settings | File Templates.
 */

//TODO - switch to raw bytes later for speed .

public class FilePersistenceManager implements PersistenceManager {

    ObjectOutputStream os=null;
    ObjectInputStream is=null;

    String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FilePersistenceManager()
    {
         init();
    }

    public FilePersistenceManager(String fileName)
    {
        this.fileName=fileName;
        init();
    }

    public void init()
    {
        try {
            is = new ObjectInputStream(new FileInputStream(fileName));

        } catch (Exception e) {
            try {
                FileOutputStream fos = new  FileOutputStream(fileName);
                os = new ObjectOutputStream(fos);
                return;
            } catch (IOException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        try {
        FileOutputStream fos = new  FileOutputStream(fileName,true);
        os = new ObjectOutputStream(fos);
        } catch (Exception e) {

        }

    }

  @Override
    public void put(Key key, Value value) {
        try {
            os.writeObject(key);
            os.writeObject(value);
            os.flush();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public Iterator iterator() {
        return new FilePersistenceManagerIterator(is);  //To change body of implemented methods use File | Settings | File Templates.
    }

}
