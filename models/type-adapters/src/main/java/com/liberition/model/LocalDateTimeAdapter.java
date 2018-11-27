package com.liberition.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    public LocalDateTime unmarshal(String s) {
        if (s != null)
            return LocalDateTime.parse(s);
        else {
            return null;
        }
    }

    public String marshal(LocalDateTime d) {
        if (d != null) {
            return DateTimeFormatter.ISO_DATE_TIME.format(d);
        } else {
            return null;
        }
    }
}
