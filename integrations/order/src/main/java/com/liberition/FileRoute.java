package com.liberition;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class FileRoute extends RouteBuilder {

    static final String ROUTEID = "route1";

    public void configure() {

        from("file:/tmp/cameldemo?include=.*.xml")
            .routeId(ROUTEID)
            .transform(method(Transformations.class, "salesOrderToTransportBooking"))
            .marshal().json(JsonLibrary.Jackson)
            .to("log:foo");

        from("timer:foo?delay=3s")
            .setBody(simple("Hello 1 ${date:now:yyyyMMddhhmmss}"))
            .to("log:foo");
    }
}
