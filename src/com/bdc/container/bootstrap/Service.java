package com.bdc.container.bootstrap;

/**
 * Created with IntelliJ IDEA.
 * User: mkhanwalker
 * Date: 10/11/12
 * Time: 3:31 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Service {
    public void init()  ;
    public void start()  ;
    public void stop()    ;
    public void pause()    ;
    public void resume()    ;
    public void destroy()    ;
    public void setName(String s);
    public String getName();

}
