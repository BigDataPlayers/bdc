package com.bdc.container.ftpservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: a09726a
 * Date: 1/9/13
 * Time: 12:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class FTPTaskList {
    Map<String,FTPTask> FTPTaskMap = new HashMap<String,FTPTask>();

    List<FTPClient> FTPTasks;

    public void setFTPTasks(List<FTPTask> FTPTasks) {

        for (FTPTask task : FTPTasks)
        {
            FTPTaskMap.put(task.getName(), task);
        }
    }

    public FTPTask get(String name)
    {
        return FTPTaskMap.get(name);
    }


}
