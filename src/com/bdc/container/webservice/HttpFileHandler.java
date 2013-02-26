package com.bdc.container.webservice;


import org.apache.http.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Locale;
import java.io.*;

import org.apache.http.entity.*;

class HttpFileHandler implements HttpRequestHandler {

    ResultProcessor processor ;

    public ResultProcessor getProcessor() {
        return processor;
    }

    public void setProcessor(ResultProcessor processor) {
        this.processor = processor;
    }

    public HttpFileHandler() {

        super();


    }




    public void handle(

            final HttpRequest request,

            final HttpResponse response,

            final HttpContext context) throws HttpException, IOException {




        String method = request.getRequestLine().getMethod().toUpperCase(Locale.ENGLISH);

        if (!method.equals("GET") && !method.equals("HEAD") && !method.equals("POST")) {

            throw new MethodNotSupportedException(method + " method not supported");

        }

        String target = request.getRequestLine().getUri();




        if (request instanceof HttpEntityEnclosingRequest) {

            HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();

            byte[] entityContent = EntityUtils.toByteArray(entity);

            System.out.println("Incoming entity content (bytes): " + entityContent.length);

        }


        System.out.println(target);
        target = target.substring(1);
        if (target.startsWith("ChartType"))
        {
           // processFile(target,response)
           String[] sA = target.split("=");
            File file = processor.getFile(sA[1]);
            response.setStatusCode(HttpStatus.SC_OK);
//            FileEntity body = new FileEntity(file, ContentType.create("binary/png"));
            FileEntity body;
            if (sA[1].equals("geoChart")|| sA[1].startsWith("cloudChart"))
            {
                System.out.println("Recd request for " + sA[1] + " will set content to binary");
                body = new FileEntity(file, ContentType.create("binary/png"));
            } else
            {
             body = new FileEntity(file, ContentType.create("text/html"));
            }
            response.setEntity(body);
            return;
        }



        response.setStatusCode(HttpStatus.SC_OK);
       // ResultProcessor processor = new ResultProcessor();
        String result = processor.getResult(target);

        StringEntity entity = new StringEntity(result);

        response.setEntity(entity);



    }



}

