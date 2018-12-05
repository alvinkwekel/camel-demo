package com.liberition;

import com.liberition.model.sales_order.v1.SalesOrder;
import com.liberition.model.transportbooking.v1.TransportBooking;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

import static com.liberition.TransformationUtils.plusBusinessDays;

@Component
public class Transformations {

    public static TransportBooking salesOrderToTransportBooking(SalesOrder salesOrder) {

        TransportBooking transportBooking = new TransportBooking();
        transportBooking.setCustomerName(salesOrder.getShippingAddress().getName());
        transportBooking.setPlannedDeliveryDate(transformDate(salesOrder.getOrderDate()));
        transportBooking.setTotalWeight(salesOrder.getItems().getItem()
            .stream()
            .mapToDouble(SalesOrder.Items.Item::getUnitWeight).sum());
        return transportBooking;
    }

    public static String transformDate(ZonedDateTime orderDate) {

        return orderDate.plusDays(2).toLocalDate().toString();
    }

}
