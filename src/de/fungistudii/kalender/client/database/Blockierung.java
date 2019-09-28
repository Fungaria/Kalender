/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.client.database;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sreis
 */
@XmlRootElement(name = "blockierung")
public class Blockierung {
    public int id;
    public Date start;
    public int dauer;
    public int friseur;
    public String msg;
}
