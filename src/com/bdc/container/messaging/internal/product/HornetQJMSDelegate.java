package com.bdc.container.messaging.internal.product;

import com.bdc.container.messaging.*;
import com.bdc.container.messaging.Destination;
import com.bdc.container.messaging.Message;
import com.bdc.container.messaging.exception.MessagingException;
import com.bdc.container.messaging.internal.HeaderImpl;
import com.bdc.container.messaging.internal.PayLoadImpl;
import com.bdc.container.messaging.internal.ResponseImpl;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

//TODO - use a logger for logging exceptions etc.
//TODO - accept ack modes
//TODO - HA / Clustering and reconenct features need to be added .


/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 8/29/12
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */

/* This class will contain the HornetQ specific code. It will be used by the Initiator and Acceptor to get the actual work done
*   One Instance should NOT be shared across Initiators and Acceptors
*
*
* */



public class HornetQJMSDelegate extends AbstractProductDelegate implements MessageListener {

    Handler handler;

    Connection connection = null;
    InitialContext initialContext = null;
    ConnectionFactory factory;
    Session session;
    javax.jms.Destination defaultDestination;
    MessageProducer producer=null;
    MessageConsumer messageConsumer=null;

    JMSTransportInfo transportInfo;   // this is the specific class as the delegate knows exactly what properties it needs.

    public void setTransportInfo(TransportInfo info)
    {
        transportInfo = (JMSTransportInfo)info;
    }

    private InitialContext getContext()
    {
        try {
        String jndiFilename = transportInfo.getJndiFileName();
        File jndiFile = new File(jndiFilename);
        Properties props = new Properties();
        FileInputStream inStream = null;
        try
        {
            inStream = new FileInputStream(jndiFile);
            props.load(inStream);
        }
        finally
        {
            if (inStream != null)
            {
                inStream.close();
            }
        }
        return new InitialContext(props);
        } catch (Exception e)
        {
            throw new MessagingException(e);
        }
    }

    Map<String,Connection>  connectionCache = new HashMap<String, Connection>();

    @Override
    public void init() {
        try {
            initialContext = getContext();
            factory = (ConnectionFactory)initialContext.lookup(transportInfo.getConnectionFactory());
            String hostInfo =   (String) initialContext.getEnvironment().get("java.naming.provider.url");
            connection = connectionCache.get(hostInfo);
            if (connection==null)  // new connection , create and add to cache
            {
                connection = factory.createConnection();
                connectionCache.put(hostInfo,connection)  ;
            }
            else // existing connection , check user preference on whether to create new or reuse
            {
               if (!transportInfo.isReuseConnection())
                       connection = factory.createConnection();
            }
            if (transportInfo.getClientId()!=null)
                connection.setClientID(transportInfo.getClientId());
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void destroy() {
        try {
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    //TODO - need a clean seperation between producer and consumer
    @Override
    public void start() {
        try {
            connection.start();
            try {
                defaultDestination= (javax.jms.Destination)initialContext.lookup(destination.getSubject());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (handler!=null)
            {
            createConsumer();
            try {

                messageConsumer.setMessageListener(this);
            } catch (JMSException e) {
                e.printStackTrace();
            }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            if (initialContext!=null)
                initialContext.close();
            if (connection!=null)
                connection.stop();

            destCache.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Map<String,javax.jms.Destination> destCache = new HashMap<String, javax.jms.Destination>();

    @Override
    public void send(Message message, Destination destination) {
        try {

            javax.jms.Destination  dest = destCache.get(destination.getSubject()) ;
            if (dest==null)
                dest= (javax.jms.Destination)initialContext.lookup(destination.getSubject());
            send(message,dest);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    //TODO - string properties assumed for now . Can be extended to capture property types and set the appropriate ones
    private void copyHeadersToJMSMessage(javax.jms.Message message, Header header) throws JMSException
    {
        if (header==null || header.getProperties().isEmpty())
            return;
       Iterator iter =header.getProperties().entrySet().iterator()  ;
        while (iter.hasNext())
        {
            Map.Entry entry = (Map.Entry)iter.next()   ;
            message.setStringProperty((String)entry.getKey(),(String)entry.getValue());
        }
    }

    private void copyHeadersFromJMSMessage(javax.jms.Message message, Header header) throws JMSException
    {

        Enumeration names = message.getPropertyNames();
        while (names.hasMoreElements())
        {
            String name = (String)names.nextElement();
            String val = message.getStringProperty(name);
            header.addProperty(name,val);

        }
    }

    ProductDelegateType type;
    public void setType(ProductDelegateType type)
    {
        this.type=type;
    }


    private void send(Message message , javax.jms.Destination destination)
    {
        try {
            if (producer==null)
            {
                producer = session.createProducer(null);
                switch (transportInfo.getDeliveryType())
                {
                    case PERSISTENT:
                        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
                        break;
                    case NONPERSISTENT:
                        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                        break;
                    default :
                        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
                }
            }

            PayLoad payload = message.getPayload();
            if (payload.getType()==PayLoadType.Text)
            {
                TextMessage msg = session.createTextMessage(payload.getPayLoadAsString());
                copyHeadersToJMSMessage(msg, message.getHeader());
                producer.send(destination,msg);
            }
            else
            {
                BytesMessage msg = session.createBytesMessage() ;
                msg.writeBytes(payload.getPayloadAsBytes());
                copyHeadersToJMSMessage(msg, message.getHeader());
                producer.send(destination,msg);

            }
        } catch (Exception e ){
            e.printStackTrace();
        }


    }

    public void send(Message message) {

        send(message,defaultDestination);

    }



    @Override
    public Message receive(int timeout) {
        javax.jms.Message messageReceived =null;
        try {
            createConsumer();
            if (timeout>=0)
                messageReceived = messageConsumer.receive(timeout);
            else
                messageReceived = messageConsumer.receive();

            return processMessage(messageReceived);
         } catch (JMSException e) {
            e.printStackTrace();
        }



        return null;

    }

    private void createConsumer()
    {
        try {
            if (messageConsumer==null)
            {
                if (transportInfo.isDurableConsumer())
                {

                   session.createDurableSubscriber((Topic)defaultDestination,transportInfo.getSubscriberName()) ;
                }
                messageConsumer = session.createConsumer(defaultDestination);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void registerCallback(Handler handler) {
        this.handler = handler;

    }

    private Message processMessage(javax.jms.Message msg)
    {
        Message message = null;
        try {
            PayLoad payload = new  PayLoadImpl();
            if (msg instanceof TextMessage)
            {
                TextMessage messageReceived =(TextMessage)msg;
                payload.setPayLoad(messageReceived.getText());
            }
            else if (msg instanceof BytesMessage)
            {
                BytesMessage messageReceived = (BytesMessage)msg;
                byte[] bytes = new byte[(int)messageReceived.getBodyLength()];
                messageReceived.readBytes(bytes);
                payload.setPayLoad(bytes);
            }

            Header header = new HeaderImpl();
            copyHeadersFromJMSMessage(msg,header)   ;
            message = new ResponseImpl(header,payload);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return message;

    }

    @Override
    public void onMessage(javax.jms.Message msg) {

        Message message  = processMessage(msg) ;
        handler.onMessage(message);
    }

    Destination destination;

    public void setDefaultDestination(Destination destination) {

        this.destination=destination;


    }

    //TODO - implement the logic behind adding and removing destinations .

    @Override
    public void addDestination(Destination destination) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeDestination(Destination destination) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}