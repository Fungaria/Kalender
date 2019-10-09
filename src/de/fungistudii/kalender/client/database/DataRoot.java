package de.fungistudii.kalender.client.database;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "de.saufgenossen.ehre")
public class DataRoot {

    @XmlElementWrapper(name = "termine")
    @XmlElement(name = "termin")
    public final ArrayList<Termin> appointments = new ArrayList<Termin>();
    
    @XmlElementWrapper(name = "blockierungen")
    @XmlElement(name = "blockierung")
    public final ArrayList<Blockierung> blockierungen = new ArrayList<Blockierung>();

    @XmlElementWrapper(name = "kunden")
    @XmlElement(name = "kunde")
    public final ArrayList<Kunde> kunden = new ArrayList<Kunde>();
    
    @XmlElementWrapper(name = "friseure")
    @XmlElement(name = "friseur")
    public final ArrayList<Friseur> friseure = new ArrayList<Friseur>();
    
    @XmlElementWrapper(name = "services")
    @XmlElement(name = "service")
    public final ArrayList<Service> services = new ArrayList<Service>();
    
    @Override
    public String toString() {
        return appointments.toString()+"\n"+kunden.toString();
    }
}
