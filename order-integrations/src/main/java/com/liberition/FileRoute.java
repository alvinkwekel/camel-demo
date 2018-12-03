package com.liberition;

import com.liberition.model.sales_order.v1.SalesOrder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class FileRoute extends RouteBuilder {

    static final String ROUTEID = "route1";

    public void configure() {

        from("file:/tmp/cameldemo?include=.*.xml")
            .routeId(ROUTEID)
            .convertBodyTo(SalesOrder.class)
            .setHeader("bc_id", simple("${body.orderNumber}"))
            .transform(method(Transformations.class, "salesOrderToTransportBooking"))
            .marshal().json(JsonLibrary.Jackson)
            .to("log:com.liberition");

        from("timer:foo?delay=3s")
            .setBody(simple("Hello 1 ${date:now:yyyyMMddhhmmss}"))
            .to("log:foo");
    }
}
