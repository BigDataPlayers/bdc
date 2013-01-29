package com.bdc.container.messaging;


import com.bdc.container.messaging.internal.product.DestinationType;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 8/30/12
 * Time: 11:43 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Destination {

    public String getSubject();
    public void setSubject(String subject);
    public void setType(DestinationType type);
    public DestinationType getType();


}