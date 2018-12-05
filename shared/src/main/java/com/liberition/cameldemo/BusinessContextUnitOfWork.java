package com.liberition.cameldemo;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.MDCUnitOfWork;
import org.apache.camel.spi.UnitOfWork;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

public class BusinessContextUnitOfWork extends MDCUnitOfWork implements UnitOfWork {

    // TODO Rename to bc. instead of bc_ to align with camel.
    public static final String MDC_BC_ID = "bc_id";
    public static final String MDC_BC_TYPE = "bc_type";
    public static final String MDC_BC_DOMAIN = "bc_domain";
    public static final String MDC_BC_SUBDOMAIN = "bc_subdomain";
    public static final String MDC_BC_GROUP = "bc_group";
    public static final String MDC_BC_SOURCE = "bc_source";
    public static final String MDC_BC_DESTINATION = "bc_destination";

    public BusinessContextUnitOfWork(Exchange exchange) {
        super(exchange);
    }

    private static void setMdc(Exchange exchange, String headerName, String mdcName) {
        String value = exchange.getIn().getHeader(headerName, String.class);
        if (StringUtils.isNotBlank(value)) {
            MDC.put(mdcName, value);
        }
    }

    public AsyncCallback beforeProcess(Processor processor, Exchange exchange, AsyncCallback callback) {
        setMdc(exchange, MDC_BC_ID, MDC_BC_ID);
        setMdc(exchange, MDC_BC_TYPE, MDC_BC_TYPE);
        setMdc(exchange, MDC_BC_DOMAIN, MDC_BC_DOMAIN);
        setMdc(exchange, MDC_BC_SUBDOMAIN, MDC_BC_SUBDOMAIN);
        setMdc(exchange, MDC_BC_GROUP, MDC_BC_GROUP);
        setMdc(exchange, MDC_BC_SOURCE, MDC_BC_SOURCE);
        setMdc(exchange, MDC_BC_DESTINATION, MDC_BC_DESTINATION);
        return super.beforeProcess(processor, exchange, callback);
    }

    public void afterProcess(Processor processor, Exchange exchange, AsyncCallback callback, boolean doneSync) {
        MDC.remove(MDC_BC_ID);
        MDC.remove(MDC_BC_TYPE);
        MDC.remove(MDC_BC_DOMAIN);
        MDC.remove(MDC_BC_SUBDOMAIN);
        MDC.remove(MDC_BC_GROUP);
        MDC.remove(MDC_BC_SOURCE);
        MDC.remove(MDC_BC_DESTINATION);
        super.afterProcess(processor, exchange, callback, doneSync);
    }

    @Override
    public UnitOfWork newInstance(Exchange exchange) {
        return new BusinessContextUnitOfWork(exchange);
    }
}
