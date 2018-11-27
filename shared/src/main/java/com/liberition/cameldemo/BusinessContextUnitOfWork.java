package com.liberition.cameldemo;

import java.lang.reflect.Field;
import java.util.Deque;
import java.util.Map;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultUnitOfWork;
import org.apache.camel.impl.MDCUnitOfWork;
import org.apache.camel.spi.RouteContext;
import org.apache.camel.spi.UnitOfWork;
import org.slf4j.MDC;

public class BusinessContextUnitOfWork extends MDCUnitOfWork implements UnitOfWork {

    public BusinessContextUnitOfWork(Exchange exchange) {
        super(exchange);
    }

    public Deque<RouteContext> getRouteContextStack() {
        try {
            Field field = DefaultUnitOfWork.class.getDeclaredField("routeContextStack");
            if (field == null) {
                return null;
            }
            field.setAccessible(true);
            return (Deque<RouteContext>) field.get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

    @Override
    public void afterProcess(Processor processor, Exchange exchange, AsyncCallback callback, boolean doneSync) {
        super.afterProcess(processor, exchange, callback, doneSync);
        if (exchange.getIn() != null) {
            if (exchange.getIn().getHeaders() != null) {
                for (Map.Entry<String, Object> entry : exchange.getIn().getHeaders().entrySet()) {
                    if (entry.getKey().startsWith("bc_")) {
                        if (entry.getValue() != null) {
                            MDC.put(entry.getKey(), entry.getValue().toString());
                        }
                    }
                }
            }
        }
    }

    @Override
    public UnitOfWork newInstance(Exchange exchange) {
        return new BusinessContextUnitOfWork(exchange);
    }
}
