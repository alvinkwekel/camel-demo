package com.liberition.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {

    public LocalTime unmarshal(String s) {
        if (s != null)
            return LocalTime.parse(s);
        else {
            return null;
        }
    }

    public String marshal(LocalTime t) {
        if (t != null) {
            return DateTimeFormatter.ISO_TIME.format(t);
        } else {
            return null;
        }
    }
}
