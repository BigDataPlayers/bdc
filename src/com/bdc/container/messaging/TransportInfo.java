package com.bdc.container.messaging;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 8/29/12
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */

//TODO - Provide an inprocess transport - blocking / unbounded / synchronous should be options supported via configuration

public interface TransportInfo {

    public Map<String,String> getProperties();
    public boolean isReuseConnection();
    public void setReuseConnection(boolean reuseConnection) ;

}