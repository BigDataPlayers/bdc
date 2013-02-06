package com.bdc.container.test;



import com.bdc.container.cache.Key;
import com.bdc.container.cache.Value;
import com.bdc.container.cache.persistence.Entry;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 12/13/12
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void main(String[] args) throws Exception
    {
        ObjectOutputStream os ;
        ObjectInputStream is;
        is = new ObjectInputStream(new FileInputStream("c:/temp/trial.dat"));
        {
         System.out.println( is.readObject());
         System.out.println(is.readObject());
        }
        os = new ObjectOutputStream(new FileOutputStream("c:/temp/trial.dat"));
        Key k = new KeyImpl("Trial");
        Value v = new ValueImpl("World");
        Entry e = new Entry(k,v);
        os.writeObject(k);
        os.writeObject(v);
        os.flush();
        os.close();

    }

}
