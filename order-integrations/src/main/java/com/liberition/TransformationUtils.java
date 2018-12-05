package com.liberition;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.function.IntFunction;

@Component
public class TransformationUtils {

    static final IntFunction<TemporalAdjuster> plusBusinessDays = days -> TemporalAdjusters.ofDateAdjuster(
        date -> {
            LocalDate baseDate =
                days > 0 ? date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                    : days < 0 ? date.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY)) : date;
            int businessDays = days + Math.min(Math.max(baseDate.until(date).getDays(), -4), 4);
            return baseDate.plusWeeks(businessDays / 5).plusDays(businessDays % 5);
        });
}
