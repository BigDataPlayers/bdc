package com.bdc.container.test;


import java.net.Socket;




import org.apache.http.ConnectionReuseStrategy;

import org.apache.http.HttpHost;

import org.apache.http.HttpRequestInterceptor;

import org.apache.http.HttpResponse;

import org.apache.http.HttpVersion;

import org.apache.http.impl.DefaultConnectionReuseStrategy;

import org.apache.http.impl.DefaultHttpClientConnection;

import org.apache.http.message.BasicHttpRequest;

import org.apache.http.params.HttpParams;

import org.apache.http.params.HttpProtocolParams;

import org.apache.http.params.SyncBasicHttpParams;

import org.apache.http.protocol.HttpContext;

import org.apache.http.protocol.BasicHttpContext;

import org.apache.http.protocol.ExecutionContext;

import org.apache.http.protocol.HttpProcessor;

import org.apache.http.protocol.HttpRequestExecutor;

import org.apache.http.protocol.ImmutableHttpProcessor;

import org.apache.http.protocol.RequestConnControl;

import org.apache.http.protocol.RequestContent;

import org.apache.http.protocol.RequestExpectContinue;

import org.apache.http.protocol.RequestTargetHost;

import org.apache.http.protocol.RequestUserAgent;

import org.apache.http.util.EntityUtils;

public class WebServiceTester {

    public static void main(String[] args) throws Exception {

        HttpParams params = new SyncBasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, "UTF-8");
        HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1");
        HttpProtocolParams.setUseExpectContinue(params, true);
        HttpProcessor httpproc = new ImmutableHttpProcessor(new HttpRequestInterceptor[] {
                new RequestContent(),
                new RequestTargetHost(),
                new RequestConnControl(),
                new RequestUserAgent(),
                new RequestExpectContinue()});
        HttpRequestExecutor httpexecutor = new HttpRequestExecutor();
        HttpContext context = new BasicHttpContext(null);
        HttpHost host = new HttpHost("localhost", 8080);
        DefaultHttpClientConnection conn = new DefaultHttpClientConnection();
        ConnectionReuseStrategy connStrategy = new DefaultConnectionReuseStrategy();
        context.setAttribute(ExecutionContext.HTTP_CONNECTION, conn);
        context.setAttribute(ExecutionContext.HTTP_TARGET_HOST, host);
        try {




            String[] targets = {

                    "/t.html"

            };




            for (int i = 0; i < targets.length; i++) {

                if (!conn.isOpen()) {

                    Socket socket = new Socket(host.getHostName(), host.getPort());

                    conn.bind(socket, params);

                }

                BasicHttpRequest request = new BasicHttpRequest("GET", targets[i]);

                System.out.println(">> Request URI: " + request.getRequestLine().getUri());




                request.setParams(params);

                httpexecutor.preProcess(request, httpproc, context);

                HttpResponse response = httpexecutor.execute(request, conn, context);

                response.setParams(params);

                httpexecutor.postProcess(response, httpproc, context);




                System.out.println("<< Response: " + response.getStatusLine());

                System.out.println(EntityUtils.toString(response.getEntity()));

                System.out.println("==============");

                if (!connStrategy.keepAlive(response, context)) {

                    conn.close();

                } else {

                    System.out.println("Connection kept alive...");

                }

            }

        } finally {

            conn.close();

        }

    }




}