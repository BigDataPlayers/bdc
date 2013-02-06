package com.bdc.container.test;


import com.bdc.container.cache.Key;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 12/13/12
 * Time: 2:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class KeyImpl implements Key {

    String name;

    public KeyImpl(String name)
    {
        this.name=name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeyImpl key = (KeyImpl) o;

        if (name != null ? !name.equals(key.name) : key.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
