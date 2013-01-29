package com.bdc.container.event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 9/20/12
 * Time: 9:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class AsyncExecutor {

    int size = 3;

    int numThreads = 4;

    List<BlockingQueue<Runnable>> workQueue = new ArrayList<BlockingQueue<Runnable>>(numThreads);

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AsyncExecutor()
    {
       init();
    }

    public AsyncExecutor(int numThreads)
    {
       this.numThreads=numThreads;
        init();
    }

    List<Thread> threads = new ArrayList<Thread>(numThreads) ;
    List<AsyncWorker> workers = new ArrayList<AsyncWorker>(numThreads);
    private void init()
    {
        for (int i=0;i<numThreads;i++)
        {
            BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(size);
            workQueue.add(queue);
            AsyncWorker worker = new AsyncWorker(queue);
            Thread t = new Thread(worker);
            workers.add(worker);
            threads.add(t);

            t.start();
        }
    }

    int roundrobin;

    public void submit(Runnable runnable)
    {
        try {
            workQueue.get(roundrobin%numThreads).put(runnable);
            roundrobin++;
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public void waitForCompletion()
    {
        for (int i=0;i<numThreads;i++)
        {
            while (!workQueue.get(i).isEmpty())
            {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

            }
        }


    }

    public void destroy()
    {
        try {
            for (int i=0; i<numThreads;i++)
            {

                workers.get(i).running=false;
                workQueue.get(i).put(new Runnable() {
                    @Override
                    public void run() {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        try {
            for (int i=0; i<numThreads;i++)
            {

                while (threads.get(i).isAlive())
                {
                    Thread.sleep(100);
                    System.out.println("Waiting for thread to fall out of run loop " + i);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }



    }

}

class AsyncWorker implements  Runnable
{
    public volatile boolean running = true ;
    BlockingQueue<Runnable> workerQueue;

    public AsyncWorker(BlockingQueue<Runnable> workerQueue)
    {
        this.workerQueue=workerQueue;
    }


    public void run()
    {
        while (running)
        {
            try {
                Runnable r =  workerQueue.take();

                r.run();
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }

    }
}
