package com.liberition.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    public LocalDate unmarshal(String s) {
        if (s != null)
            return LocalDate.parse(s);
        else {
            return null;
        }
    }

    public String marshal(LocalDate d) {
        if (d != null) {
            return DateTimeFormatter.ISO_DATE.format(d);
        } else {
            return null;
        }
    }
}
