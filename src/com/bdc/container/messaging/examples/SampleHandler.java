package com.bdc.container.messaging.examples;


import com.bdc.container.messaging.*;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 8/29/12
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class SampleHandler  implements Handler {

    @Override
    public void onMessage(Message message) {

        System.out.println(Thread.currentThread().getName());
        if (message==null) // ignore
            return;
        PayLoad payload = message.getPayload();
        if (payload.getType()== PayLoadType.Text)
        {
            System.out.println("Text " + message.getPayload().getPayLoadAsString());
        }
        else if (payload.getType()==PayLoadType.Binary)
        {
            byte[] b = payload.getPayloadAsBytes();
            System.out.println("Binary " + new String(b));
        }

        Header header = message.getHeader();
        if (header!=null && !header.isEmpty())
             System.out.println(header.getProperties().toString());
    }
}
