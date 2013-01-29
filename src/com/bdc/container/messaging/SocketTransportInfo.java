package com.bdc.container.messaging;



import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 9/12/12
 * Time: 11:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class SocketTransportInfo implements TransportInfo {

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

    String hostname;
    int port;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
