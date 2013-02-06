package com.bdc.container.messaging;



import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 8/29/12
 * Time: 1:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class StompTransportInfo implements TransportInfo {

    String userID;
    String password;
    String hostname;
    int port;

    boolean reuseConnection=true; // Multiple sessions will be created with the same connection for a given broker if this is true.

    public boolean isReuseConnection() {
        return reuseConnection;
    }

    public void setReuseConnection(boolean reuseConnection) {
        this.reuseConnection = reuseConnection;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    @Override
    public Map<String, String> getProperties() {

        Map<String,String> map = new HashMap<String, String>();
        map.put("userID",userID);
        map.put("password",password);
        map.put("hostname",hostname);
        map.put("port",String.valueOf(port));

        return map;
    }
}
