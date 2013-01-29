package com.bdc.container.webservice;

import java.io.IOException;


import com.bdc.container.bootstrap.Service;
import org.apache.http.ConnectionClosedException;

import org.apache.http.HttpEntity;

import org.apache.http.HttpEntityEnclosingRequest;

import org.apache.http.HttpException;

import org.apache.http.HttpRequest;

import org.apache.http.HttpResponse;

import org.apache.http.HttpResponseInterceptor;

import org.apache.http.HttpServerConnection;

import org.apache.http.HttpStatus;

import org.apache.http.MethodNotSupportedException;

import org.apache.http.entity.ContentType;

import org.apache.http.entity.FileEntity;

import org.apache.http.entity.StringEntity;

import org.apache.http.impl.DefaultConnectionReuseStrategy;

import org.apache.http.impl.DefaultHttpResponseFactory;

import org.apache.http.impl.DefaultHttpServerConnection;

import org.apache.http.params.CoreConnectionPNames;

import org.apache.http.params.HttpParams;

import org.apache.http.params.CoreProtocolPNames;

import org.apache.http.params.SyncBasicHttpParams;

import org.apache.http.protocol.HttpContext;

import org.apache.http.protocol.BasicHttpContext;

import org.apache.http.protocol.HttpProcessor;

import org.apache.http.protocol.HttpRequestHandler;

import org.apache.http.protocol.HttpRequestHandlerRegistry;

import org.apache.http.protocol.HttpService;

import org.apache.http.protocol.ImmutableHttpProcessor;

import org.apache.http.protocol.ResponseConnControl;

import org.apache.http.protocol.ResponseContent;

import org.apache.http.protocol.ResponseDate;

import org.apache.http.protocol.ResponseServer;

import org.apache.http.util.EntityUtils;


public class WebServer implements Service {

    @Override
    public void init() {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    Thread t ;
    @Override
    public void start() {

        try {
            Thread t = new Thread(new RequestListener(port,processor));
            t.start();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    int port=8080;

  ResultProcessor processor;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ResultProcessor getProcessor() {
        return processor;
    }

    public void setProcessor(ResultProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void stop() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void pause() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setName(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public static void main(String[] args) throws Exception {


    }


}