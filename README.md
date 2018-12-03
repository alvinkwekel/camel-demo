# File Component
## Consumer Options
https://github.com/apache/camel/blob/master/camel-core/src/main/docs/file-component.adoc
move=done
delete=true
noop=true
## Write
.to("file:/tmp/cameldemo?fileName=transport-booking.json")

# Develop Route
## Split
https://github.com/apache/camel/tree/master/camel-core/src/main/docs/eips
.convertBodyTo(SalesOrderList.class)
.split(simple("${body.salesOrder}"))
## ONGL
.log("body.customerName")
## Logging MDC
.convertBodyTo(SalesOrder.class)
.setHeader("bc_id", simple("${body.orderNumber}"))

# Testing
orderDate.with(plusBusinessDays.apply(2)).toLocalDate();
Update json to 2018-10-02

# CI/CD
from("timer:foo?delay=3s")
    .setBody(simple("Hello 1 ${date:now:yyyyMMddhhmmss}"))
    .to("log:foo");
    
    
    
