/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.database.adapters;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author sreis
 */
public class TimeAdapter extends XmlAdapter<String, LocalTime> {

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public String marshal(LocalTime v) throws Exception {
        synchronized (dateFormat) {
            return dateFormat.format(v);
        }
    }

    @Override
    public LocalTime unmarshal(String v) throws Exception {
        synchronized (dateFormat) {
            return LocalTime.parse(v, DateTimeFormatter.ofPattern("HH:mm:ss"));
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
