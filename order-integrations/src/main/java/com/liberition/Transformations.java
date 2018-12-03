package com.liberition;

import com.liberition.model.sales_order.v1.SalesOrder;
import com.liberition.model.transportbooking.v1.TransportBooking;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.function.IntFunction;

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

    static final IntFunction<TemporalAdjuster> plusBusinessDays = days -> TemporalAdjusters.ofDateAdjuster(
        date -> {
            LocalDate baseDate =
                days > 0 ? date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                    : days < 0 ? date.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY)) : date;
            int businessDays = days + Math.min(Math.max(baseDate.until(date).getDays(), -4), 4);
            return baseDate.plusWeeks(businessDays / 5).plusDays(businessDays % 5);
        });
}
