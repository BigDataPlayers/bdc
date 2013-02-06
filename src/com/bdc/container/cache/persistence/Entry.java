package com.bdc.container.cache.persistence;


import com.bdc.container.cache.Key;
import com.bdc.container.cache.Value;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 12/12/12
 * Time: 4:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class Entry {
    Key key ;
    Value value;

    public Entry()
    {

    }

    public Entry(Key key , Value value)
    {
        this.key=key;
        this.value = value;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

}
