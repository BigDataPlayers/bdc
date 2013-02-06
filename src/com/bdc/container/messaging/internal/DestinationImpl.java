package com.bdc.container.messaging.internal;

import com.bdc.container.messaging.Destination;
import com.bdc.container.messaging.internal.product.DestinationType;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 8/30/12
 * Time: 11:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class DestinationImpl  implements Destination {
       String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    DestinationType type;

    @Override
    public void setType(DestinationType type) {
            this.type=type;
    }

    @Override
    public DestinationType getType() {
        return type;
    }
}
