package com.bdc.container.webservice;


import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpException;
import org.apache.http.HttpServerConnection;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpService;

import java.io.IOException;

class Worker implements Runnable {



    private final HttpService httpservice;

    private final HttpServerConnection conn;

    volatile boolean running = true;


    public Worker(

            final HttpService httpservice,

            final HttpServerConnection conn) {

        super();

        this.httpservice = httpservice;

        this.conn = conn;

    }

    public void stop()
    {
        running = false;
    }




    @Override
              //TODO - this while loop has to change to be able to reuse the threads more eficiently .
    public void run() {

        System.out.println("New connection thread");

        HttpContext context = new BasicHttpContext(null);

        try {

            while (running && !Thread.interrupted() && this.conn.isOpen()) {

                this.httpservice.handleRequest(this.conn, context);

            }

        } catch (ConnectionClosedException ex) {

            System.err.println("Client closed connection");

        } catch (IOException ex) {

            System.err.println("I/O error: " + ex.getMessage());

        } catch (HttpException ex) {

            System.err.println("Unrecoverable HTTP protocol violation: " + ex.getMessage());

        } finally {

            try {

                this.conn.shutdown();

            } catch (IOException ignore) {}

        }

    }




}