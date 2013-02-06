package com.bdc.container.messaging.internal.product;



import com.bdc.container.messaging.*;

import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 9/11/12
 * Time: 9:16 AM
 * To change this template use File | Settings | File Templates.
 */

/*
 */

public class InMemoryQDelegate extends AbstractProductDelegate {

    BlockingQueue<Message> defaultQueue;
    static ConcurrentMap<String,BlockingQueue<Message>> queues = new ConcurrentHashMap<String, BlockingQueue<Message>>();
    static ConcurrentMap<String,ReceiveProxy> proxies = new  ConcurrentHashMap<String,ReceiveProxy>();;



    @Override
    public void init() {
        defaultQueue = queues.get(defaultDestination.getSubject());
        if (defaultQueue==null)
        {
           if (transportInfo.isBoundedSize())
              defaultQueue = new ArrayBlockingQueue(transportInfo.getSize());
            else
              defaultQueue = new LinkedBlockingDeque<Message>();
            queues.put(defaultDestination.getSubject(), defaultQueue);
        }

        if (type== ProductDelegateType.Acceptor)
        {
            addReceiveProxy(defaultQueue,defaultDestination);
        }
    }


    // for a Queue - add a new thread/proxy for every handler
    // for a topic add a new handler to an existing proxy (if it exists)
    private void addReceiveProxy(BlockingQueue queue, Destination destination)
    {

        if (destination.getType()== DestinationType.Topic)
        {
            ReceiveProxy proxy = proxies.get(destination.getSubject());
            if (proxy!=null)
            {
                proxy.addHandler(handler);
                return ;
            }
        }
            ReceiveProxy proxy =new ReceiveProxy(queue);
            proxy.addHandler(handler);
            proxies.put(destination.getSubject(),proxy);
            Thread t = new Thread(proxy);
            t.start();
    }

    @Override
    public void destroy() {
       // NOP for inmemory queue for now
    }

    @Override
    public void start() {
        //NOP for the inmemory queue for now
    }

    @Override
    public void stop() {
        //NOP for the inmemory queue for now
    }

    @Override
    public void send(Message message, Destination destination) {
       BlockingQueue<Message> queue = queues.get(destination.getSubject());
       if (queue!=null)
       {
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
       }
        else
       {
           System.out.println("Queue not found , please use add Destination to add the queue");
       }
    }

    @Override
    public void send(Message message) {
        try {
            defaultQueue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    // timeout is ignored
    public Message receive(int timeout) {

        System.out.println("NOT Supported yet");
        return null ;
    }



    InMemoryTransportInfo transportInfo;
    @Override
    public void setTransportInfo(TransportInfo info) {
        transportInfo=(InMemoryTransportInfo)info;
    }

    @Override
    public void addDestination(Destination destination) {
        BlockingQueue<Message> queue = queues.get(destination.getSubject());
        if (queue==null)
        {
            if(transportInfo.isBoundedSize())
                    queue= new ArrayBlockingQueue<Message>(transportInfo.getSize());
            else
                    queue = new LinkedBlockingDeque<Message>();
            queues.put(destination.getSubject(),queue);
        }
        if (type==ProductDelegateType.Acceptor)
        {
            addReceiveProxy(queue, destination);
        }
    }

    @Override
    public void removeDestination(Destination destination) {
        queues.remove(destination.getSubject());
        ReceiveProxy proxy = proxies.get(destination.getSubject());
        if (proxy!=null)
            proxy.setRunning(false);
        proxies.remove(destination.getSubject());
    }


}
