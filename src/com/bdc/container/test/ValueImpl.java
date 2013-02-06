package com.bdc.container.test;

import com.bdc.container.cache.Value;


/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 12/13/12
 * Time: 2:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValueImpl implements Value {

    String value;

    public ValueImpl(String value)
    {
        this.value=value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValueImpl value1 = (ValueImpl) o;

        if (value != null ? !value.equals(value1.value) : value1.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
