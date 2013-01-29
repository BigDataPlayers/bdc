package com.bdc.container.messaging.internal;



import com.bdc.container.messaging.Header;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 8/29/12
 * Time: 2:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class HeaderImpl implements Header {

   Map<String,String> keyVal = new HashMap<String, String>();  // todo - later see if header props need to be anything other than strings

    public void addProperty(String key , String val)
    {
        keyVal.put(key,val);
    }

    public Map getProperties()
    {
        return keyVal;
    }

    public boolean isEmpty()
    {
        return keyVal.isEmpty();
    }

    public String toString()
    {
        return keyVal.toString();
    }

    public String getProperty(String key)
    {
        return keyVal.get(key);
    }

}
