/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.database;

import de.fungistudii.kalender.database.adapters.DateTimeAdapter;
import de.fungistudii.kalender.database.adapters.MapElement;
import java.time.LocalDateTime;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author sreis
 */
@XmlRootElement(name = "blockierung")
public class Blockierung extends MapElement{
    @XmlAttribute
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    public LocalDateTime start;
    @XmlAttribute
    public int duration;
    @XmlAttribute
    public int friseur;
    @XmlAttribute
    public String msg;

    public Blockierung() {
    }

    public Blockierung(LocalDateTime start, int duration, int friseur, String msg) {
        this.start = start;
        this.duration = duration;
        this.friseur = friseur;
        this.msg = msg;
    }
}
