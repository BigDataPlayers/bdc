package com.bdc.container.ftpservice;

/**
 * Created with IntelliJ IDEA.
 * User: a09726a
 * Date: 1/9/13
 * Time: 10:17 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseFTPClient implements FTPClient {
    public String name ;
    public String hostname;
    public int port = 21;
    public String user;
    public String password;
    public String localDirectory;
    public String remoteDirectory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocalDirectory() {
        return localDirectory;
    }

    public void setLocalDirectory(String localDirectory) {
        this.localDirectory = localDirectory;
    }

    public String getRemoteDirectory() {
        return remoteDirectory;
    }

    public void setRemoteDirectory(String remoteDirectory) {
        this.remoteDirectory = remoteDirectory;
    }

    enum FTPCodes
    {
        FAILURE(550),SUCCESS(220);

        int value;
        FTPCodes(int value)
        {

            this.value=value;

        }

        public int getValue()
        {
            return this.value;
        }
    }  ;

}
