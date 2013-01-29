package com.bdc.container.messaging;


import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 8/29/12
 * Time: 1:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Header {

    public void addProperty(String key, String val);
    public Map getProperties();
    public boolean isEmpty();
    public String getProperty(String key);
}
