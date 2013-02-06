package com.bdc.container.ftpservice;

/**
 * Created with IntelliJ IDEA.
 * User: a09726a
 * Date: 1/9/13
 * Time: 10:15 AM
 * To change this template use File | Settings | File Templates.
 */
public interface FTPClient {
    public void setName (String name) ;
    public String getName () ;
    public void connect()   ;
    public void disconnect() ;
    public boolean send(String srcFileName , String tgtFileName);
    public boolean receive(String srcFileName , String tgtFileName) ;
    public boolean receive(String filePatternString) ;
    public boolean delete(String tgtFileName);
    public void setHostname(String hostname);
    public void setPort(int port) ;
    public void setUser(String user);
    public void setPassword(String password);
    public void setLocalDirectory(String localDirectory);
    public void setRemoteDirectory(String remoteDirectory) ;
}
