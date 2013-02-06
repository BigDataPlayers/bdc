package com.bdc.container.messaging.internal.product;


import com.bdc.container.messaging.Destination;
import com.bdc.container.messaging.Handler;
import com.bdc.container.messaging.ProductDelegate;
import com.bdc.container.messaging.ProductDelegateType;

/**
 * Created with IntelliJ IDEA.
 * User: a09198a
 * Date: 9/10/12
 * Time: 11:47 AM
 * To change this template use File | Settings | File Templates.
 */

//TODO - Move common getters / setters and attributes across the various delegate implementations here to avoid code duplication
public abstract class AbstractProductDelegate implements ProductDelegate {

    protected Destination defaultDestination;
    @Override
    public void setDefaultDestination(Destination destination) {
        defaultDestination=destination;
    }

    protected Handler handler ;
    @Override
    public void registerCallback(Handler handler) {
        this.handler = handler;
    }

    ProductDelegateType type;

    @Override
    public void setType(ProductDelegateType type) {
        this.type=type;
    }


}
