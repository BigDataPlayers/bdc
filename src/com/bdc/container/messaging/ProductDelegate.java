package com.bdc.container.messaging;



/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 8/29/12
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */

public interface ProductDelegate  {
    public void init();
    public void destroy();
    public void start();
    public void stop();
    public void send(Message message, Destination destination);
    public void send(Message message);
    public Message receive(int timeout);
    public void registerCallback(Handler handler);
    public void setDefaultDestination(Destination destination) ;
    public void setTransportInfo(TransportInfo info);
    public void addDestination(Destination destination) ;
    public void removeDestination(Destination destination) ;
    public void setType(ProductDelegateType type);

}

//TODO - add ability to start and stop thru mbeans
//TODO - reconnection
//TODO - see if this needs to be refactored into 2 interfaces .