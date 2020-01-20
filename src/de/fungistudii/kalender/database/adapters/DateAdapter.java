package de.fungistudii.kalender.database.adapters;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * i like to go on dates, very romantic and stuff
 * @author sreis
 */
public class DateAdapter extends XmlAdapter<String, LocalDate> {

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String marshal(LocalDate v) throws Exception {
        synchronized (dateFormat) {
            return dateFormat.format(v);
        }
    }

    @Override
    public LocalDate unmarshal(String v) throws Exception {
        synchronized (dateFormat) {
            return LocalDate.parse(v, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
