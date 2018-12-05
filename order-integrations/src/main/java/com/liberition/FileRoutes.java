package com.liberition;

import com.liberition.model.sales_order.v1.SalesOrder;
import com.liberition.model.sales_order.v1.SalesOrderList;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class FileRoutes extends RouteBuilder {

    static final String ROUTEID = "route1";

    public void configure() {

        from("file:/tmp/cameldemo?include=.*.xml")
            .routeId(ROUTEID)
            .transform(method(Transformations.class, "salesOrderToTransportBooking"))
            .to("log:com.liberition");

        from("timer:foo?delay=3s")
            .setBody(simple("Hello KLM ${date:now:yyyyMMddhhmmss}"))
            .to("log:foo");
    }
}
