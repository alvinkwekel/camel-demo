# File component

# Consumer Options
move=done
delete=true
noop=true

## ONGL
.log("body.customerName")

## Split
.convertBodyTo(SalesOrderList.class)
.split(simple("${body.salesOrder}"))

## Write
.marshal().json(JsonLibrary.Jackson)
.to("file:/tmp/cameldemo?fileName=transport-booking.json")