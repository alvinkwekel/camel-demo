package com.liberition.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeAdapter extends XmlAdapter<String, ZonedDateTime> {

    public ZonedDateTime unmarshal(String s) {
        if (s != null)
            return ZonedDateTime.parse(s);
        else {
            return null;
        }
    }

    public String marshal(ZonedDateTime d) {
        if (d != null) {
            return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(d);
        } else {
            return null;
        }
    }
}
