/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.database;

import de.fungistudii.kalender.database.adapters.DateAdapter;
import de.fungistudii.kalender.database.adapters.MapElement;
import java.time.LocalDate;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author sreis
 */
@XmlRootElement(name = "vacation")
public class Vacation extends MapElement{
    @XmlAttribute
    @XmlJavaTypeAdapter(DateAdapter.class)
    public LocalDate start;
    @XmlAttribute
    @XmlJavaTypeAdapter(DateAdapter.class)
    public LocalDate end;

    public Vacation() {
    }

    public Vacation(int id, LocalDate start, LocalDate end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return id+"-  start:"+start+"   end:"+end;
    }
}
