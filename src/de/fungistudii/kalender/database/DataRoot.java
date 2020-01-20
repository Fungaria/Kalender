package de.fungistudii.kalender.database;

import de.fungistudii.kalender.database.adapters.BlockAdapter;
import de.fungistudii.kalender.database.adapters.ClientAdapter;
import de.fungistudii.kalender.database.adapters.ServiceAdapter;
import de.fungistudii.kalender.database.adapters.ServiceCategoryAdapter;
import de.fungistudii.kalender.database.adapters.TerminAdapter;
import de.fungistudii.kalender.database.adapters.WorkerAdapter;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(namespace = "de.fungistudii.kalender")
public class DataRoot {

    @XmlElement(name = "termine")
    @XmlJavaTypeAdapter(TerminAdapter.class)
    public final HashMap<Integer, Termin> appointments = new HashMap<>();

    @XmlElement(name = "blockierungen")
    @XmlJavaTypeAdapter(BlockAdapter.class)
    public final HashMap<Integer, Blockierung> blockierungen = new HashMap<>();

    @XmlElement(name = "kunden")
    @XmlJavaTypeAdapter(ClientAdapter.class)
    public final HashMap<Integer, Customer> kunden = new HashMap<>();

    @XmlElement(name = "friseure")
    @XmlJavaTypeAdapter(WorkerAdapter.class)
    public final HashMap<Integer, Friseur> friseure = new HashMap<>();

    @XmlElement(name = "services")
    @XmlJavaTypeAdapter(ServiceAdapter.class)
    public final HashMap<Integer, Service> services = new HashMap<>();

    @XmlElement(name = "serviceCategories")
    @XmlJavaTypeAdapter(ServiceCategoryAdapter.class)
    public final HashMap<Integer, ServiceCategory> serviceCategories = new HashMap<>();

    @Override
    public String toString() {
        return appointments.toString() + "\n" + kunden.toString();
    }
}
