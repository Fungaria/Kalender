package de.fungistudii.kalender.client.database;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "de.saufgenossen.ehre")
public class DataRoot {

    @XmlElementWrapper(name = "kalender")
    @XmlElement(name = "termin")
    public final ArrayList<Termin> termine = new ArrayList<Termin>();

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
        return termine.toString()+"\n"+kunden.toString();
    }
}
