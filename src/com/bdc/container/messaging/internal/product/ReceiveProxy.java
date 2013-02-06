package com.bdc.container.messaging.internal.product;



import com.bdc.container.messaging.Handler;
import com.bdc.container.messaging.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

class ReceiveProxy implements Runnable
{
    BlockingQueue<Message> queue;
    List<Handler> handlers;


    public void addHandler(Handler handler)
    {
        handlers.add(handler);
    }

    public ReceiveProxy(BlockingQueue<Message>queue)
    {
        this.queue = queue;
        handlers = new ArrayList<Handler>();
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    volatile boolean running=true;
    @Override
    public void run() {

        while (running)
        {
            try {
                Message message = queue.take();
                for (Handler handler : handlers)
                    handler.onMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }

    }
}
