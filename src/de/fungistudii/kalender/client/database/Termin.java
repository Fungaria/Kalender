/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.client.database;
import de.fungistudii.kalender.client.database.adapters.DateAdapter;
import de.fungistudii.kalender.client.database.adapters.MapElement;
import java.util.Date;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "termin")
public class Termin extends MapElement{
    @XmlAttribute
    public int kundenid;
    @XmlAttribute
    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date start;
    @XmlAttribute
    public int dauer;
    @XmlAttribute
    public int service;
    @XmlAttribute
    public int friseur;
    @XmlAttribute
    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date erstellt;
    @XmlAttribute
    public int urheber;

    @Override
    public String toString() {
        return "Termin{" + "id=" + id + ", kundenid=" + kundenid + ", start=" + start + ", dauer=" + dauer + ", service=" + service + ", friseur=" + friseur + '}';
    }
}
