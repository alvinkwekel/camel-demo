package com.liberition;

import com.liberition.model.sales_order.v1.SalesOrder;
import com.liberition.model.transportbooking.v1.TransportBooking;
import org.springframework.stereotype.Component;

@Component
public class Transformations {

    public static TransportBooking salesOrderToTransportBooking(SalesOrder salesOrder) {

        TransportBooking transportBooking = new TransportBooking();
        transportBooking.setCustomerName(salesOrder.getShippingAddress().getName());
        transportBooking.setPlannedDeliveryDate(salesOrder.getOrderDate().plusDays(2).toString());
        transportBooking.setTotalWeight(salesOrder.getItems().getItem()
            .stream()
            .mapToDouble(SalesOrder.Items.Item::getUnitWeight).sum());
        return transportBooking;
    }
}
