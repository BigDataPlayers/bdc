package com.bdc.container.logger;

import org.apache.commons.logging.Log;

import org.apache.commons.logging.LogFactory;




/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 10/21/12
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class Logger {


    private static Log log = LogFactory.getLog("Container");

    public static Log getLog()
    {
        return log;
    }

}

