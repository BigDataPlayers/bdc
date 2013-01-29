package com.bdc.container.bootstrap;



/**
 * Created with IntelliJ IDEA.
 * User: mkhanwalker
 * Date: 10/12/12
 * Time: 10:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServerShutDownHook implements Runnable {

  Server server;
    public ServerShutDownHook(Server s)
    {
        server=s;
    }

    public void run()
    {

       // server.stop();
       // server.destroy();

    }
}
