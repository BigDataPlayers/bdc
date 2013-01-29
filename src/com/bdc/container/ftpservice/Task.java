package com.bdc.container.ftpservice;

/**
 * Created with IntelliJ IDEA.
 * User: a09726a
 * Date: 12/15/12
 * Time: 11:30 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Task {

    public String name ;
    TaskActionHandler actionHandler = null ;

    public void setName (String taskName) {
        name = taskName;
    }

    public String getName (){
        return name;
    }

    public TaskActionHandler getActionHandler() {
        return actionHandler;
    }

    public void setActionHandler(TaskActionHandler actionHandler) {
        this.actionHandler = actionHandler;
    }


}
