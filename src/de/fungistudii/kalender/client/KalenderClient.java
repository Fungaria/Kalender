package de.fungistudii.kalender.client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import de.fungistudii.kalender.database.DataRoot;
import de.fungistudii.kalender.database.Friseur;
import de.fungistudii.kalender.database.Customer;
import de.fungistudii.kalender.database.Termin;
import de.fungistudii.kalender.client.NetworkData.*;
import de.fungistudii.kalender.database.Blockierung;
import de.fungistudii.kalender.database.Service;
import de.fungistudii.kalender.database.ServiceCategory;
import de.fungistudii.kalender.database.Vacation;
import de.fungistudii.kalender.client.listeners.*;
import de.fungistudii.kalender.database.serializers.DateSerializer;
import de.fungistudii.kalender.database.serializers.DateTimeSerializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        k.register(LocalDate.class, new DateSerializer());
        k.register(LocalTime.class);
        k.register(LocalDateTime.class, new DateTimeSerializer());
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
