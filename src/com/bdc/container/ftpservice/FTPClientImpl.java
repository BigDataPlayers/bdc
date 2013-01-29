package com.bdc.container.ftpservice;

import org.apache.commons.net.ftp.FTPClientConfig;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 12/11/12
 * Time: 10:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class FTPClientImpl extends BaseFTPClient {
    org.apache.commons.net.ftp.FTPClient ftp;

    public FTPClientImpl()
    {
        ftp = new org.apache.commons.net.ftp.FTPClient();
        FTPClientConfig config = new FTPClientConfig();
        ftp.configure(config );
    }

    public void connect()
    {
        try {
            ftp.connect(hostname,port);
            System.out.print(ftp.getReplyString());
            ftp.user(user);
            System.out.print(ftp.getReplyString());
            ftp.pass(password);
            System.out.print(ftp.getReplyString());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
     }

    public void disconnect()
    {
        try {
            ftp.logout();
            if(ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch(IOException ioe) {
                    // do nothing
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    //TODO - support for binary , support for changing directory

    public boolean send(String srcFileName , String tgtFileName)
    {
        try {
            InputStream input;
            input = new FileInputStream(srcFileName);
            System.out.println(ftp.printWorkingDirectory());
            System.out.println(ftp.printWorkingDirectory());
            ftp.storeFile(tgtFileName, input);
            input.close();
            System.out.println(ftp.getReplyString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
    }

    public boolean  receive(String srcFileName , String tgtFileName)
    {
        try {
            OutputStream output;
            output = new FileOutputStream(tgtFileName);
            ftp.retrieveFile(srcFileName, output);
            output.close();

            if (ftp.getReplyCode()==FTPCodes.FAILURE.getValue())
                return false;
            else
                return true;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
    }

    public boolean delete(String fileName) {
        // TODO test this method carefully, use at your own risk!
        try {
            return ftp.deleteFile(fileName);
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean receive(String filePatternString) {
        //todo implementation pending
        return true;
    }

}
