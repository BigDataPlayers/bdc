package com.bdc.container.messaging.internal;


import com.bdc.container.messaging.*;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 8/29/12
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class InitiatorImpl  implements Initiator {

    ProductDelegate delegate;

    public void init() {
        this.delegate.setType(ProductDelegateType.Initiator);
        this.delegate.init();
    }

    public void destroy() {
        this.delegate.destroy();
    }

    public void start() {
        this.delegate.start();
    }

    public void stop() {
        this.delegate.stop();
    }

    public void send(Message message, Destination destination) {
        this.delegate.send(message,destination);
    }

    public void send(Message message) {
        this.delegate.send(message);
    }


    public void setDelegate(ProductDelegate delegate) {
        this.delegate = delegate;
    }

    public void setDefaultDestination(Destination destination) {
        this.delegate.setDefaultDestination(destination);
    }

    public void setTransportInfo(TransportInfo info)
    {
        delegate.setTransportInfo(info);
    }

}
