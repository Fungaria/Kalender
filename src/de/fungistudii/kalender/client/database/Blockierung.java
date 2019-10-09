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

/**
 *
 * @author sreis
 */
@XmlRootElement(name = "blockierung")
public class Blockierung {
    public int id;
    @XmlElement(name = "start", required = true) 
    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date start;
    public int dauer;
    public int friseur;
    public String msg;

    @Override
    public String toString() {
        return dauer+"         "+start;
    }
    
    
}
