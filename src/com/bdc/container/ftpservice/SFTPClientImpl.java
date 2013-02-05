package com.experian.ems.universe.container.ftpservice;

/**
 * Created with IntelliJ IDEA.
 * User: a09726a
 * Date: 1/8/13
 * Time: 9:20 PM
 * To change this template use File | Settings | File Templates.
 */

import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.auth.StaticUserAuthenticator;
import org.apache.commons.vfs2.impl.DefaultFileSystemConfigBuilder;
import org.apache.commons.vfs2.provider.local.LocalFile;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;


public class SFTPClientImpl extends BaseFTPClient {

    String remotePath;
    File localDirFile;
    FileSystemManager fsManager = null;
    FileSystemOptions opts = null;
    FileObject sftpFile;

    public SFTPClientImpl() {
        System.out.println("SFTP download");
        opts = new FileSystemOptions();
    }

    /**
     * Creates the download directory localDir if it
     * does not exist and makes a connection to the remote SFTP server.
     */
//    public void initialize() {
//        if (localDirFile == null) localDirFile = new File(localDirectory);
//        if (!this.localDirFile.exists()) localDirFile.mkdirs();
//
//
//    } // initialize()


    @Override
    public void connect() {
//        initialize();
       try {
            fsManager = VFS.getManager();
        } catch (FileSystemException ex) {
            throw new RuntimeException("failed to get fsManager from VFS", ex);
        }

        UserAuthenticator auth = new StaticUserAuthenticator(null, user, password);
        try {
            DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(opts, auth);
        } catch (FileSystemException ex) {
            throw new RuntimeException("setUserAuthenticator failed", ex);
        }

        remotePath = "sftp://" + user + "@" + hostname + ":" + port + remoteDirectory;

        // Set starting path on remote SFTP server.
        try {
            SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
//            this.sftpFile = this.fsManager.resolveFile(remotePath, opts);
//            System.out.println("SFTP connection successfully established to " + remotePath);
        } catch (FileSystemException ex) {
            throw new RuntimeException("SFTP error parsing path " + remoteDirectory, ex);
        }

    }

    @Override
    public boolean send(String srcFileName, String tgtFileName) {
        try {
            sftpFile = fsManager.resolveFile(remotePath + "/" + tgtFileName, opts);
            System.out.println("SFTP connection successfully established to " + remotePath);
            String srcPath = localDirectory + File.separator + srcFileName;
            FileObject localFileObject = fsManager.resolveFile(srcPath);
            sftpFile.copyFrom(localFileObject, Selectors.SELECT_SELF);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;

        }
    }

    /**
     * Retrieves files that match the specified FileSpec from the SFTP server
     * and stores them in the local directory.
     */
    @Override
    public boolean receive(String filePatternString) {

        FileObject[] children;
        Pattern filePattern;
        filePattern = Pattern.compile(filePatternString);


        // Get a directory listing
        try {
            sftpFile = fsManager.resolveFile(remotePath, opts);
            System.out.println("SFTP connection successfully established to " + remotePath);
            children = sftpFile.getChildren();
        } catch (FileSystemException ex) {
            System.out.println("Error collecting directory listing of " + remotePath);
            return false;
        }

        boolean fileFound = false;
        for (FileObject f : children) {
            try {
                String relativePath = File.separatorChar + f.getName().getBaseName();

                if (f.getType() == FileType.FILE) {
                    System.out.println("Examining remote file " + f.getName());

                    if (!filePattern.matcher(f.getName().getPath()).matches()) {
                        System.out.println("  Filename does not match, skipping file ." + relativePath);
                        continue;
                    }
                    fileFound = true;
                    String localUrl = "file://" + localDirectory + relativePath;
                    String standardPath = localDirectory + relativePath;
                    System.out.println("  Standard local path is " + standardPath);
                    LocalFile localFile = (LocalFile) fsManager.resolveFile(localUrl);
                    System.out.println("    Resolved local file name: " + localFile.getName());

                    if (!localFile.getParent().exists()) {
                        localFile.getParent().createFolder();
                    }

                    System.out.println("  ### Retrieving file ###");
                    localFile.copyFrom(f, new AllFileSelector());
                } else {
                    System.out.println("Ignoring non-file " + f.getName());
                }
            } catch (FileSystemException ex) {
                System.out.println("Error getting file type for " + f.getName());
                return false;
            }
        } // for (FileObject f : children)

        return fileFound;
    }

    @Override
    public boolean receive(String srcFileName, String tgtFileName) {
        try {
            sftpFile = fsManager.resolveFile(remotePath + "/" + tgtFileName, opts);
            System.out.println("SFTP connection successfully established to " + remotePath);
            String srcPath = localDirectory + File.separator + srcFileName;
            FileObject localFileObject = fsManager.resolveFile(srcPath);
            localFileObject.copyFrom(sftpFile, Selectors.SELECT_SELF);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
    }

    @Override
    public boolean delete(String tgtFileName) {
        try {
            sftpFile = this.fsManager.resolveFile(remotePath + "/" + tgtFileName, opts);
            System.out.println("SFTP connection successfully established to " + remotePath);
            return sftpFile.delete();
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Release system resources, close connection to the filesystem.
     */
    @Override
    public void disconnect() {
        FileSystem fs = null;
        try {
            if (sftpFile != null) {
                sftpFile.close();
                fs = sftpFile.getFileSystem(); // This works even after the src is closed.
                fsManager.closeFileSystem(fs);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}

