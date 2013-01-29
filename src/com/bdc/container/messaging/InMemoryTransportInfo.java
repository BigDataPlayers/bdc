package com.bdc.container.messaging;



import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 9/11/12
 * Time: 9:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class InMemoryTransportInfo implements TransportInfo {

    @Override
    public Map<String, String> getProperties() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isReuseConnection() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setReuseConnection(boolean reuseConnection) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    int size=100;

    boolean boundedSize=true;

    public boolean isBoundedSize() {
        return boundedSize;
    }

    public void setBoundedSize(boolean boundedSize) {
        this.boundedSize = boundedSize;
    }

    public int getSize(){
        return size;
    }

    public void setSize(int size)
    {
        this.size=size;
    }
}
