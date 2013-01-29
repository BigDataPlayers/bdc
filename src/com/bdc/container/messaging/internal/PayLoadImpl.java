package com.bdc.container.messaging.internal;


import com.bdc.container.messaging.PayLoad;
import com.bdc.container.messaging.PayLoadType;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 8/29/12
 * Time: 3:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class PayLoadImpl  implements PayLoad {

    Object payL;

    PayLoadType type;

    public void setPayLoad(String str)
    {
        this.payL = str;
        this.type=PayLoadType.Text;
    }

    public void setPayLoad(Object obj)
    {
        this.payL = obj;
    }

    public void setPayLoad(byte[] bytes)
    {
        this.payL = bytes;
        this.type= PayLoadType.Binary;
    }

    public String getPayLoadAsString()
    {
        return (String)payL;
    }

    @Override
    public byte[] getPayloadAsBytes() {
        return (byte[])payL;
    }

    @Override
    public PayLoadType getType() {
        return type;
    }
}

