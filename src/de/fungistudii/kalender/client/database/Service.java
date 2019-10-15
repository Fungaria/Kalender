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

    @Override
    public String toString() {
        return name;
    }
}
