package com.bdc.container.messaging;



import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 8/29/12
 * Time: 1:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class JMSTransportInfo implements TransportInfo {

    String jndiFileName;
    String connectionFactory = "/ConnectionFactory";
    boolean reuseConnection=true; // Multiple sessions will be created with the same connection for a given broker if this is true.
    DeliveryType deliveryType  = DeliveryType.PERSISTENT;

    // these two properties are used only for topics . unfortunately durable subscribers cannot be created directly on the destination.
    boolean durableConsumer=false;
    String subscriberName = null ;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    String clientId=null;



    public String getSubscriberName() {
        return subscriberName;
    }

    public void setSubscriberName(String subscriberName) {
        this.subscriberName = subscriberName;
    }

    public boolean isDurableConsumer() {
        return durableConsumer;
    }

    public void setDurableConsumer(boolean durableConsumer) {
        this.durableConsumer = durableConsumer;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public boolean isReuseConnection() {
        return reuseConnection;
    }

    public void setReuseConnection(boolean reuseConnection) {
        this.reuseConnection = reuseConnection;
    }

    public String getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(String connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public String getJndiFileName() {
        return jndiFileName;
    }

    public void setJndiFileName(String jndiFileName) {
        this.jndiFileName = jndiFileName;
    }

    @Override
    public Map<String, String> getProperties() {
        Map<String,String> map = new HashMap<String, String>();
        map.put("jndiFileName",jndiFileName);
        map.put("connectionFactory",connectionFactory);

        return map;
    }
}
