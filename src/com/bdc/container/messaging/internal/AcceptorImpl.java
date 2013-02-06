package com.bdc.container.messaging.internal;


import com.bdc.container.messaging.*;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 8/29/12
 * Time: 2:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class AcceptorImpl implements Acceptor {

    ProductDelegate delegate;

    public void init() {
        this.delegate.setType(ProductDelegateType.Acceptor);
        delegate.init();
    }


    public void destroy() {
        delegate.destroy();
    }


    public void start() {
        delegate.start();
    }


    public void stop() {
        delegate.stop();
    }



    public Message receive(int timeout) {
        return delegate.receive(timeout);
    }


    public void setCallback(Handler handler) {
        delegate.registerCallback(handler);
    }


    public void setDelegate(ProductDelegate delegate) {
        this.delegate=delegate;
    }

    public void setDefaultDestination(Destination destination) {
        this.delegate.setDefaultDestination(destination);
    }

    public void addDestination(Destination destination)
    {
        delegate.addDestination(destination);
    }

    public void removeDestination(Destination destination)
    {
        delegate.removeDestination(destination);
    }

    public void setTransportInfo(TransportInfo info)
    {
        delegate.setTransportInfo(info);
    }


}
