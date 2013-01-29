package com.bdc.container.messaging;



/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 8/29/12
 * Time: 1:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PayLoad {
    public void setPayLoad(String str);
    public void setPayLoad(byte[] bytes);
    public String getPayLoadAsString();
    public byte[] getPayloadAsBytes();
    public PayLoadType getType();
}
