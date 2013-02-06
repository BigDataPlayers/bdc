package com.bdc.container.messaging;




/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 8/29/12
 * Time: 1:47 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract interface Message {

    public Header getHeader() ;

    public void setHeader(Header header) ;

    public PayLoad getPayload() ;

    public void setPayload(PayLoad payload) ;

}
