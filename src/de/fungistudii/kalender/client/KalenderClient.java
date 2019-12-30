package de.fungistudii.kalender.client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import de.fungistudii.kalender.client.database.DataRoot;
import de.fungistudii.kalender.client.database.Friseur;
import de.fungistudii.kalender.client.database.Customer;
import de.fungistudii.kalender.client.database.Termin;
import de.fungistudii.kalender.client.NetworkData.*;
import de.fungistudii.kalender.client.database.Blockierung;
import de.fungistudii.kalender.client.database.Service;
import de.fungistudii.kalender.client.database.ServiceCategory;
import de.fungistudii.kalender.client.database.Vacation;
import de.fungistudii.kalender.client.listeners.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author sreis
 */
public class KalenderClient extends Client{
    private final int TIMEOUT = 5000;
    private final int TCP_PORT = 54777;
    private final int UDP_PORT = 54779;
    
    private final String ip = "localhost"; 

    public KalenderClient() {
        registerClasses();
        registerListeners();
    }

    public void connectToServer() throws IOException {
        super.start();
        super.connect(TIMEOUT, ip, TCP_PORT, UDP_PORT);
    }
    
    public void registerClasses(){
        Kryo k = getKryo();

        k.register(ArrayList.class);
        k.register(HashMap.class);
        k.register(HashMap.Entry.class);
        k.register(DataRoot.class);
        k.register(Date.class);
        k.register(Customer.class);
        k.register(Friseur.class);
        k.register(Vacation.class);
        k.register(Service.class);
        k.register(ServiceCategory.class);
        k.register(Termin.class);
        k.register(Blockierung.class);
        k.register(LoginRequest.class);
        k.register(LoginResponse.class);
        k.register(CreateTerminRequest.class);
        k.register(StornoRequest.class);
        k.register(BlockRequest.class);
        k.register(EditVacationRequest.class);
        k.register(RemoveVacationRequest.class);
        k.register(CreateVacationRequest.class);
        k.register(RemoveBlockRequest.class);
        k.register(CreateServiceRequest.class);
        k.register(RemoveServiceRequest.class);
    }
    
    public void registerListeners(){
        super.addListener(new DataListener());
        super.addListener(new LoginListener());
        super.addListener(new TerminListener());
        super.addListener(new BlockListener());
        super.addListener(new DatabaseListener());
        super.addListener(new VacationListener());
        super.addListener(new ServiceListener());
    }
}
