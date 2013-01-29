package com.bdc.container.messaging.internal.product;


import com.bdc.container.messaging.*;
import com.bdc.container.messaging.internal.HeaderImpl;
import com.bdc.container.messaging.internal.PayLoadImpl;
import com.bdc.container.messaging.internal.ResponseImpl;

import javax.jms.JMSException;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//TODO - use a logger for logging exceptions etc.
//TODO - HA / Clustering and reconenct features need to be added .

//TODO - header support
//TODO - study and implement the rest of the verbs in the protocol   - discard verbs like connected , process error frames , send heartbeat at regular intervals.
// TODO - Persistent and Durable options for sender and receiver

//TODO - get rid of all magic numbers are replace with settable properties.

//TODO - indicate whether this delegate is for initiator or acceptor - makes things easier
// TODO - for initiator setup a listener to get messages from server (e.g. connected , error etc. )
//TODO - make this class thread safe as multiple threads can then be used to call the same initiator . - mostly done , just review to check if anything was missed.

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 8/29/12
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */

/* this class will contain the HornetQ specific code. It will be used by the Initiator and Acceptor to get the actual work done */

// IMPORTANT : Since this provides

public class HornetQStompDelegate extends AbstractProductDelegate {

    Handler handler;

     StompTransportInfo transportInfo;   // this is the specific class as the delegate knows exactly what properties it needs.

    public void setTransportInfo(TransportInfo info)
    {
        transportInfo = (StompTransportInfo)info;
    }

    private String encoding =  "UTF-8";

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    protected void sendFrame(Socket socket, String data) throws Exception
    {
        boolean validConnection=true;
        try {
        reserveConnection();
        byte[] bytes = data.getBytes(encoding);
        OutputStream outputStream = socket.getOutputStream();
        for (int i = 0; i < bytes.length; i++)
        {
            outputStream.write(bytes[i]);
        }
        outputStream.flush();
        } catch (Exception e) {
            validConnection=false;
            System.out.println("Disconnected while sending frame - retrying");
           // e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            removeConnection();
            reconnect();
        } finally {
            if (validConnection)
                returnConnection();
        }
    }


    Lock connectionLock = new ReentrantLock() ;

   protected Socket socket=null;

   static Timer heartBeatTimer= new Timer();

    static final String END_OF_FRAME = "\u0000";
    static final String END_OF_LINE = "\n";

    private void reconnect()
    {
        task.cancel();  // cancel the existing heartbeat.
        init();
        start();
    }

    @Override
    public void init() {
        while(true)
        {
        try {
            reserveConnection();
            if (socket==null)
                {
                    socket = new Socket(transportInfo.getHostname(), transportInfo.getPort());
                }
                return ;
        } catch (Throwable e) {
            //e.printStackTrace();
            System.out.println("Not connected - retrying");
            waitBeforeRetry();
        } finally {
            returnConnection();
        }
        }

    }

    int reconnectInterval = 5000;   // in millis
    private void  waitBeforeRetry()
    {
        try {
            Thread.sleep(reconnectInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void destroy() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    StompHeartbeatTask task ;

    //TODO - move to string builder.
    @Override
    public void start() {
        try {
            String connectFrame = "CONNECT" + END_OF_LINE  +
                    "heart-beat:0,0" + END_OF_LINE  +
                    "login:" + transportInfo.getUserID()+ END_OF_LINE +
                    "passcode:" + transportInfo.getPassword()+ END_OF_LINE +
                    "request-id: 1" + END_OF_LINE +
                    END_OF_LINE +
                    END_OF_FRAME;
            sendFrame(socket, connectFrame);
            task = new StompHeartbeatTask(this);
            heartBeatTimer.schedule(task, 50000, 50000);

            if (callbackThread!=null)  // hack for now to execute this only for the acceptor
            {
            if (!subscribed)
                Subscribe(defaultDestination.getSubject());

            callbackThread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    //TODO - check if explicit unsubscribe needs to be sent to hornetQ before sending disconnect.
    @Override
    public void stop() {

        try {
            heartBeatTimer.cancel();
            String disconnectFrame = "DISCONNECT" + END_OF_LINE+
                    END_OF_LINE +
                    END_OF_FRAME;
            sendFrame(socket, disconnectFrame);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    private void removeConnection()
    {
        socket=null;
    }

    private void reserveConnection()
    {
           connectionLock.lock();
    }

    private void returnConnection()
    {
            connectionLock.unlock();
    }

    @Override
    public void send(Message message, Destination destination) {
        try {
            PayLoad payload = message.getPayload();
            String body;
            if (payload.getType()==PayLoadType.Text)
                 body = payload.getPayLoadAsString();
            else
                body= new String(payload.getPayloadAsBytes());
            Header header = message.getHeader();
            StringBuilder headerBuilder =null;
            headerBuilder = copyHeadersToStompMessage(header);
            StringBuilder msg =  new StringBuilder();
            msg.append("SEND" + END_OF_LINE);
            msg.append("destination:"+ destination.getSubject()+ END_OF_LINE);
            if (payload.getType()==PayLoadType.Binary)
                msg.append("content-length:" +  body.length()  + END_OF_LINE) ;
            if (headerBuilder!=null)
                msg.append(headerBuilder);
            msg.append(END_OF_LINE);
            msg.append(body);
            msg.append(END_OF_FRAME);
            sendFrame(socket, msg.toString());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    //TODO - string properties assumed for now . Can be extended to capture property types and set the appropriate ones
    private StringBuilder copyHeadersToStompMessage( Header header) throws JMSException
    {
       if (header==null || header.getProperties().isEmpty())
            return null;

        StringBuilder builder = new StringBuilder();
       Iterator iter =header.getProperties().entrySet().iterator()  ;
        while (iter.hasNext())
        {
            Map.Entry entry = (Map.Entry)iter.next()   ;
            builder.append((String)entry.getKey() + ":" + (String)entry.getValue() + END_OF_LINE);
        }

        return builder;
    }



    public void send(Message message) {
       send(message,defaultDestination);
    }



    @Override
    public Message receive(int timeout) {

        if (!subscribed)
            Subscribe(defaultDestination.getSubject());

        return recvFrame();
    }

    private void printFrame(char[] data, int count)
    {
        for (int i=0;i<count;i++)
            System.out.print(data[i]);
    }


    int MAXRECVSIZE = 10;
    private Message recvFrame()
    {
        try {
            InputStream inputStream = socket.getInputStream();
            char[] data = new char[MAXRECVSIZE];
            int count=0;
            while(true)
            {
                int x = inputStream.read();
                if (x==-1)
                {
                    System.out.println("disconnected - attempting reconnect");
                    reconnect();
                    Subscribe();
                    return null ;
                    //break;
                }
                if (x==0)
                {
                    Message message = convertFrameToMessage(data,count);
                    return message;
                }
                data[count++] = (char)x;
                if (count==MAXRECVSIZE)
                    data = resizeRecvBuffer(data);
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        return null ;

    }


    //TODO - if connection is to be muleiplexed across acceptors , then would have to read the destination on the message and send it to the appropriate handler .

    private Message convertFrameToMessage(char[] rawData, int length)
    {
          //TODO - Need a more efficient convertor here - the back and forth between bytes , char and data is inefficient .

       String data = new String(rawData,0,length);
     //   if (data.startsWith("\nMESSAGE\n"))
        {
            Header header = new HeaderImpl();
            String[] firstParse = data.split("\n");
            for (int i=2; i<firstParse.length-1;i++)
            {
                String s = firstParse[i];
                String[] nextParse = s.split(":");
                if (nextParse.length==2) // ignore additional \n
                {
                    header.addProperty(nextParse[0],nextParse[1]);
                }
            }
            PayLoad payload = new PayLoadImpl();
            if (header.getProperty("content-length")!=null)
            {
                payload.setPayLoad(firstParse[firstParse.length-1].getBytes());
            }
            else
            {
                payload.setPayLoad(firstParse[firstParse.length-1]);
            }
            Message m = new ResponseImpl(header,payload)  ;
            return m;
        }
    }

    private char[]  resizeRecvBuffer(char[] data)
    {
        MAXRECVSIZE*=2;
        char[] data1 = new char[MAXRECVSIZE];
        System.arraycopy(data,0,data1,0,MAXRECVSIZE/2);
        return data1;
    }


    private void Subscribe()
    {
        Subscribe(defaultDestination.getSubject());
        for (String subject : subjects)
        {
            Subscribe(subject);
        }
    }

    private void UnSubscribe()
    {
        Subscribe(defaultDestination.getSubject());
        for (String subject : subjects)
        {
            UnSubscribe(subject);
        }
    }


    private void UnSubscribe(String subject)
    {
        SubscribeUnsubscribeFrame(subject,"UNSUBSCRIBE");

    }

    private void Subscribe(String subject)
    {
        SubscribeUnsubscribeFrame(subject,"SUBSCRIBE");
    }

    private void SubscribeUnsubscribeFrame(String subject, String action)
    {
        try {
            StringBuilder msg =  new StringBuilder();
            msg.append(action + END_OF_LINE);
            msg.append("id:"+Math.abs(subject.hashCode())+END_OF_LINE);    // use   subject hashcode to generate unique id value .
            msg.append("destination:"+ subject+ END_OF_LINE);
            msg.append(END_OF_LINE);
            msg.append(END_OF_FRAME);
            sendFrame(socket, msg.toString());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    private volatile boolean subscribed = false;


    Thread callbackThread=null;
    Thread keepAliveThread=null;

    //TODO - switch to a timer task and one thread across all acceptors connecting to a given url to send the heartback to the server .

    //TODO - consider the thread safety of all collections ..

    Set<String> subjects = new HashSet<String>();

    public void addDestination(Destination destination)
    {
        subjects.add(destination.getSubject());
        Subscribe(destination.getSubject());
    }

    public void removeDestination(Destination destination)
    {
        subjects.remove(destination.getSubject());
        UnSubscribe(destination.getSubject());
    }

    ProductDelegateType type;
    public void setType(ProductDelegateType type)
    {
        this.type=type;
    }


    @Override
    public void registerCallback(Handler handler) {

        this.handler = handler;
        callbackThread = new Thread(new HandleResponse(handler));
      //  t.setDaemon(true);

    }

    private class HandleResponse implements Runnable
    {
        Handler handler;
        volatile boolean active=true;

        public void setStatus(boolean status)
        {
            active=status;
        }
        public HandleResponse(Handler handler)
        {
            this.handler = handler;

        }

        public void run()
        {
            while(active)
                {
                try {
                    handler.onMessage(recvFrame());
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }

    }

    Destination defaultDestination ;

    public void setDefaultDestination(Destination destination) {

        defaultDestination=destination;
    }
}