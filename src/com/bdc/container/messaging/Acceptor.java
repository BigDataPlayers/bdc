package com.bdc.container.messaging;



/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 8/29/12
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Acceptor {
    public void init();
    public void destroy();
    public void start();
    public void stop();
    public Message receive(int timeout);
    public void setDelegate(ProductDelegate delegate);
    public void setDefaultDestination(Destination destination);
    public void addDestination(Destination destination);
    public void removeDestination(Destination destination);
    public void setTransportInfo(TransportInfo info);
    public void setCallback(Handler handler);


}
