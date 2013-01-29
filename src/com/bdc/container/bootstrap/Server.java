package com.bdc.container.bootstrap;


import com.bdc.container.logger.Logger;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.management.*;
import java.lang.management.ManagementFactory;


/**
 * Created with IntelliJ IDEA.
 * User: mkhanwalker
 * Date: 10/11/12
 * Time: 2:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class Server implements ServerMBean {
    String config;
    ApplicationContext context;
    ServiceList list;
    ServerMode mode = ServerMode.Batch;
    static Server server = new Server();
    Log log = Logger.getLog();

    public static Server getInstance()
    {
        return server;
    }

    public ServiceList getList() {
        return list;
    }

    public void setList(ServiceList list) {
        this.list = list;
    }


    public String getConfig() {
        return config;
    }

     void setConfig(String config) {
        this.config = config;
    }

    private Server()
    {
    }


    public void init()
    {
        try {
            context = new FileSystemXmlApplicationContext(config);
            System.out.println("Context defined " + context);
            list = (ServiceList) context.getBean("ServiceList");
            context.getBean("exporter");
            ManagementFactory.getPlatformMBeanServer().registerMBean(this,new ObjectName("com.bdc.container:type=Server"));
            for (Service s : list.getServices())
            {
                s.init();
            }
        } catch (InstanceAlreadyExistsException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (MBeanRegistrationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NotCompliantMBeanException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

    public void start()
    {
        log.info("Starting Services");
        for (Service s : list.getServices())
        {
            s.start();
        }
    }


    public void stop()
    {
        log.info("Stopping Services");

        for (Service s : list.getServices())
        {
            s.stop();
        }

    }

    public void pause()
    {
        for (Service s : list.getServices())
        {
            s.pause();
        }
    }

    public void resume()
    {
        for (Service s : list.getServices())
        {
            s.resume();
        }
    }

    public void destroy()
    {
        Service[] services = (Service[])list.getServices().toArray()   ;

        for (int i=services.length-1; i>0;i--)
        {
            Service service = services[i];
            service.destroy();
        }

        synchronized (this)
        {
            notify();
        }

    }

    public ApplicationContext getContext()
    {
        return context;
    }

    public ServerMode getMode() {
        return mode;
    }

    public void setMode(ServerMode mode) {
        this.mode = mode;
    }

    public static void main(String[] args)
    {
        Server s =  Server.getInstance();
        s.setConfig(args[0]);
        String mode = System.getProperty("Mode","Batch");
        if (mode.equalsIgnoreCase("Daemon"))
            s.setMode(ServerMode.Daemon);
        Runtime.getRuntime().addShutdownHook(new Thread(new ServerShutDownHook(s)));
        s.init();
        s.start();
        if (s.mode==ServerMode.Batch)
        {
            s.stop();
            s.destroy();
        }
        else
        {
            s.waitForExit();

        }
    }

    private synchronized void waitForExit() {

            try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

    }
 }
