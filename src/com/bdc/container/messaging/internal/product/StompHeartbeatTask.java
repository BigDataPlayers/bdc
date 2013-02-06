package com.bdc.container.messaging.internal.product;



import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 9/4/12
 * Time: 3:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class StompHeartbeatTask  extends TimerTask {

    /* Without a heartbeat , hornetQ disconnects the client after N secs of inactivity - default is 60 secs .  Stomp allows for a heartbeat to be sent to the server to tell
    it that the client is still connected.
     */

    /*TODO - check with hornetQ folks two things about their stomp implementation - End of Frame as per the protocol is ^@ but in the sample code they use \u0000.
    //TODO Heartbeat should be \n - while that works (ie client does not get disconnected), the server seems to be throwing an exception on getting the message.
    //TODO - how to use a cluster with Stomp
    //TODO - how to use Persistent and durable subscribers with stomp is not clear from the stomp specs or documentstion from hornet.
    */
   HornetQStompDelegate delegate;
    @Override
    public void run() {
        try {
            delegate.sendFrame(delegate.socket, delegate.END_OF_LINE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public StompHeartbeatTask(HornetQStompDelegate delegate)
    {
        this.delegate=delegate;
    }
}
