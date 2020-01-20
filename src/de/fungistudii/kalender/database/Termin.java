/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.database;
import de.fungistudii.kalender.database.adapters.DateAdapter;
import de.fungistudii.kalender.database.adapters.DateTimeAdapter;
import de.fungistudii.kalender.database.adapters.MapElement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "termin")
public class Termin extends MapElement{
    @XmlAttribute
    public int kundenid;
    @XmlAttribute
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    public LocalDateTime start;
    @XmlAttribute
    public int duration;
    @XmlAttribute
    public int service;
    @XmlAttribute
    public int friseur;
    @XmlAttribute
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    public LocalDateTime erstellt;
    @XmlAttribute
    public int urheber;
    @XmlAttribute
    public String notes;
    @XmlAttribute
    public String color;
    @XmlAttribute
    public int paidPrice;

    @Override
    public String toString() {
        return "Termin{" + "id=" + id + ", kundenid=" + kundenid + ", start=" + start + ", dauer=" + duration + ", service=" + service + ", friseur=" + friseur + '}';
    }
}
