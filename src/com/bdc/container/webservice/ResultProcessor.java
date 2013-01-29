package com.bdc.container.webservice;


import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: mkhanwalker
 * Date: 11/16/12
 * Time: 12:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ResultProcessor {

    public String getResult(String aid)  ;
    public File getFile(String input);

}
