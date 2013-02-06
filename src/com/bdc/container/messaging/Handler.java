package com.bdc.container.messaging;



/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 8/29/12
 * Time: 1:52 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Handler {

    public void onMessage(Message message);
}
