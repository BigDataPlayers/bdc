package com.bdc.container.webservice;



import org.apache.http.HttpResponseInterceptor;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.DefaultHttpServerConnection;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.protocol.*;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class RequestListener implements Runnable{




    private final ServerSocket serversocket;

    private final HttpParams params;

    private final HttpService httpService;

    //TODO - change this to use the event framework

    ExecutorService service = Executors.newCachedThreadPool();

     HttpFileHandler handler;

    ResultProcessor processor;


    public RequestListener(int port, ResultProcessor processor ) throws IOException {

        this.processor=processor;

        this.serversocket = new ServerSocket(port);

        this.params = new SyncBasicHttpParams();

        this.processor = processor;

        this.params

                .setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 5000)

                .setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 8 * 1024)

                .setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, false)

                .setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true)

                .setParameter(CoreProtocolPNames.ORIGIN_SERVER, "HttpComponents/1.1");




        // Set up the HTTP protocol processor

        HttpProcessor httpproc = new ImmutableHttpProcessor(new HttpResponseInterceptor[] {

                new ResponseDate(),

                new ResponseServer(),

                new ResponseContent(),

                new ResponseConnControl()

        });




        // Set up request handlers

        HttpRequestHandlerRegistry reqistry = new HttpRequestHandlerRegistry();

        //TODO - might need to support multiple handlers for different patterns .
        handler = new HttpFileHandler();
        handler.setProcessor(processor);
        reqistry.register("*", handler);




        // Set up the HTTP service

        this.httpService = new HttpService(

                httpproc,

                new DefaultConnectionReuseStrategy(),

                new DefaultHttpResponseFactory(),

                reqistry,

                this.params);

    }






    public void run() {

        System.out.println("Listening on port " + this.serversocket.getLocalPort());

        while (!Thread.interrupted()) {

            try {

                // Set up HTTP connection

                Socket socket = this.serversocket.accept();

                DefaultHttpServerConnection conn = new DefaultHttpServerConnection();

                System.out.println("Incoming connection from " + socket.getInetAddress());

                conn.bind(socket, this.params);



                Worker worker = new  Worker(this.httpService, conn);
                service.submit(worker);


            } catch (InterruptedIOException ex) {

                break;

            } catch (IOException e) {

                System.err.println("I/O error initialising connection thread: "

                        + e.getMessage());

                break;

            }

        }

    }

}

