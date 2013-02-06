package com.bdc.container.messaging;


import com.bdc.container.bootstrap.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 10/20/12
 * Time: 4:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessagingService implements Service {
    
    List<Initiator> initiatorList;

    List<Acceptor> acceptorList;

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public List<Initiator> getInitiatorList() {
        return initiatorList;
    }

    public void setInitiatorList(List<Initiator> initiatorList) {
        this.initiatorList = initiatorList;
    }

    public List<Acceptor> getAcceptorList() {
        return acceptorList;
    }

    public void setAcceptorList(List<Acceptor> acceptorList) {
        this.acceptorList = acceptorList;
    }

    @Override
    public void init() {
        for (Acceptor acceptor : acceptorList)
            acceptor.init();

        for (Initiator initiator : initiatorList)
            initiator.init();

    }

    @Override
    public void start() {
        for (Acceptor acceptor : acceptorList)
            acceptor.start();

        for (Initiator initiator : initiatorList)
            initiator.start();

    }

    // todo - put an initiator pool and get from pool using a name for the initiator
    public Initiator getInitiator()
    {
        return initiatorList.get(0);
    }

    @Override
    public void stop() {
        for (Initiator initiator : initiatorList)
            initiator.stop();

        for (Acceptor acceptor : acceptorList)
            acceptor.stop();



    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {
        for (Initiator initiator : initiatorList)
            initiator.destroy();

        for (Acceptor acceptor : acceptorList)
            acceptor.destroy();


    }
}
