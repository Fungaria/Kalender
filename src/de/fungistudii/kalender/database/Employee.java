/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.database;

import de.fungistudii.kalender.database.adapters.MapElement;
import de.fungistudii.kalender.database.adapters.VacationAdapter;
import java.time.LocalDate;
import java.util.HashMap;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author sreis
 */
@XmlRootElement(name = "employee")
public class Employee extends MapElement{
    @XmlAttribute
    public String surname;
    @XmlAttribute
    public String name;
    @XmlElement
    @XmlJavaTypeAdapter(VacationAdapter.class)
    public final HashMap<Integer, Vacation> vacations = new HashMap<>();
    
    @Override
    public String toString() {
        return surname+" "+name;
    }
    
    public boolean hasVacation(LocalDate date){
        return vacations.values().stream().anyMatch((v) -> !(date.isBefore(v.start) || date.isAfter(v.end)));
    }
}
