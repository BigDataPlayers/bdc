package com.bdc.container.messaging.internal.product;


import com.bdc.container.messaging.*;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 9/12/12
 * Time: 11:26 AM
 * To change this template use File | Settings | File Templates.
 */

//todo - look at netty to provide the socket library instead of writing one from scratch .
public class SocketDelegate extends AbstractProductDelegate {

    @Override
    public void init() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void start() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void stop() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void send(Message message, Destination destination) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void send(Message message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Message receive(int timeout) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void registerCallback(Handler handler) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setDefaultDestination(Destination destination) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setTransportInfo(TransportInfo info) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addDestination(Destination destination) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeDestination(Destination destination) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setType(ProductDelegateType type) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
