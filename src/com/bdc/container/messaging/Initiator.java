package com.bdc.container.messaging;



/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 8/29/12
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Initiator  {
    public void init();
    public void destroy();
    public void start();
    public void stop();
    public void send(Message message, Destination destination);
    public void send(Message message);
    public void setDelegate(ProductDelegate delegate);
    public void setDefaultDestination(Destination destination);
    public void setTransportInfo(TransportInfo info);


}
