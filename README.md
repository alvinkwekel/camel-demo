# 1
## Payload type
.marshal().json(JsonLibrary.Jackson)
.convertBodyTo(String.class)

## File component consumer options
https://github.com/apache/camel/blob/master/camel-core/src/main/docs/file-component.adoc
move=done
delete=true
noop=true

## Write
.marshal().json(JsonLibrary.Jackson)
.to("file:/tmp/cameldemo?fileName=transport-booking.json")

## ONGL
Transport booking (after transform)
.log("body.customerName")

## Logging MDC
.convertBodyTo(SalesOrder.class)
.setHeader("bc_id", simple("${body.orderNumber}"))

## Split Pattern
https://github.com/apache/camel/tree/master/camel-core/src/main/docs/eips
Use list file
.convertBodyTo(SalesOrderList.class)
.split(simple("${body.salesOrder}"))
.to("file:/tmp/cameldemo?fileName=transport-booking-${header.bc_id}.json");


# 2
## Testing
orderDate.with(plusBusinessDays.apply(2)).toLocalDate();
Update json to 2018-10-02


#3
## CI/CD
from("timer:foo?delay=3s")
    .setBody(simple("Hello KLM ${date:now:yyyyMMddhhmmss}"))
    .to("log:foo");
    
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
    
    
    


#1
from("file:/tmp/cameldemo?include=.*.xml")
            .routeId(ROUTEID)
            .convertBodyTo(SalesOrderList.class)
            .split(simple("${body.salesOrder}"))
            .setHeader("bc_id", simple("${body.orderNumber}"))
            .transform(method(Transformations.class, "salesOrderToTransportBooking"))
            .log("body.customerName")
            .marshal().json(JsonLibrary.Jackson)
            .to("file:/tmp/cameldemo?fileName=transport-booking-${header.bc_id}.json");