/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.client.database;

import de.fungistudii.kalender.client.database.adapters.MapElement;
import de.fungistudii.kalender.client.database.adapters.VacationAdapter;
import java.util.Date;
import java.util.HashMap;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author sreis
 */
@XmlRootElement(name = "friseur")
public class Friseur extends MapElement{
    @XmlAttribute
    public String name;
    @XmlElement
    @XmlJavaTypeAdapter(VacationAdapter.class)
    public final HashMap<Integer, Vacation> vacations = new HashMap<>();
    
    @Override
    public String toString() {
        return name;
    }
    
    public boolean hasVacation(Date date){
        return vacations.values().stream().anyMatch((v) -> !(date.before(v.start) || date.after(v.end)));
    }
}
