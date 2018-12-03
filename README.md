# File Component
## Consumer Options
https://github.com/apache/camel/blob/master/camel-core/src/main/docs/file-component.adoc
move=done
delete=true
noop=true
## Write
.marshal().json(JsonLibrary.Jackson)
.to("file:/tmp/cameldemo?fileName=transport-booking.json")
## Exception Handling

# Develop Route
## Split
.convertBodyTo(SalesOrderList.class)
.split(simple("${body.salesOrder}"))
## ONGL
.log("body.customerName")
## Logging MDC
.convertBodyTo(SalesOrder.class)
.setHeader("bc_id", simple("${body.orderNumber}"))


# CI/CD
from("timer:foo?delay=3s")
    .setBody(simple("Hello 1 ${date:now:yyyyMMddhhmmss}"))
    .to("log:foo");
    
    
    
