package com.bdc.container.messaging.internal;


import com.bdc.container.messaging.Header;
import com.bdc.container.messaging.PayLoad;
import com.bdc.container.messaging.Request;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 8/29/12
 * Time: 3:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequestImpl implements Request {

    Header header;
    PayLoad payload;
    public RequestImpl(Header header, PayLoad payload)
    {
        this.header=header;
        this.payload=payload;
    }

    @Override
    public Header getHeader() {
        return header;
    }

    @Override
    public void setHeader(Header header) {
        this.header=header;
    }

    @Override
    public PayLoad getPayload() {
        return payload;
    }

    @Override
    public void setPayload(PayLoad payload) {
        this.payload=payload;
    }
}
