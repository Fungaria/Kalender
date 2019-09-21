/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.client.database;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "termin")
public class Termin {
    public int id;
    public int kundenid;
    
    @XmlElement(name = "start", required = true) 
    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date start;
    public int dauer;
    public int service;
    public int friseur;

    @Override
    public String toString() {
        return "Termin{" + "id=" + id + ", kundenid=" + kundenid + ", start=" + start + ", dauer=" + dauer + ", service=" + service + ", friseur=" + friseur + '}';
    }
}
