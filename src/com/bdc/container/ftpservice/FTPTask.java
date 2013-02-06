package com.bdc.container.ftpservice;
/**
 * Created with IntelliJ IDEA.
 * User: a09726a
 * Date: 12/15/12
 * Time: 11:30 AM
 * To change this template use File | Settings | File Templates.
 */

public class FTPTask extends Task {
    String hostName;
    int port = 21;
    String user;
    String password;
    String localDirectory = "";
    String remoteDirectory = "";
    String localFile= "";
    String remoteFile= "";
    FTPClient manager;

    public void setManager(FTPClient manager) {
        this.manager = manager;
    }

    public FTPClient getManager() {
        return manager;
    }

    public String getHostName() {
        return hostName;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getLocalDirectory() {
        return localDirectory;
    }

    public String getRemoteDirectory() {
        return remoteDirectory;
    }

    public String getLocalFile() {
        return localFile;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLocalDirectory(String localDirectory) {
        this.localDirectory = localDirectory;
    }

    public void setRemoteDirectory(String remoteDirectory) {
        this.remoteDirectory = remoteDirectory;
    }

    public void setLocalFile(String localFile) {
        this.localFile = localFile;
    }

    public String getRemoteFile() {
        return remoteFile;
    }

    public void setRemoteFile(String remoteFile) {
        this.remoteFile = remoteFile;
    }

    public boolean send () {
//        configure ();
//        manager.connect();
        return manager.send(localFile, remoteFile)    ;
    }

    public boolean receive () {
    //        manager.connect();
        return manager.receive(localFile, remoteFile) ;
    }

    public boolean receiveAll() {
        return manager.receive(remoteFile);
    }

    private void configure() {
        manager.setHostname(hostName);
        manager.setPort(port);
        manager.setLocalDirectory(localDirectory);
        manager.setRemoteDirectory(remoteDirectory);
        manager.setUser(user);
        manager.setPassword(password);
    }

    public void connect  (){
        configure();
        manager.connect();
    }

    public void disconnect () {
        manager.disconnect();
    }
}
