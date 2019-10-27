/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.client.database;

import de.fungistudii.kalender.client.database.adapters.MapElement;
import de.fungistudii.kalender.client.database.adapters.VacationAdapter;
import java.util.HashMap;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/** 
 *
 * @author sreis
 */
@XmlRootElement(name = "service")
public class Service extends MapElement{
    @XmlAttribute
    public String name;
    
    @XmlAttribute
    public int category;
    
    @XmlAttribute
    public int duration;
    
    @XmlAttribute
    public int einwirkZeit;
    
    @XmlAttribute
    public int price;

    public Service() {
    }

    public Service(int id, String name, int category, int duration, int einwirkZeit, int price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.duration = duration;
        this.einwirkZeit = einwirkZeit;
        this.price = price;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
