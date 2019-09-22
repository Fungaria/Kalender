package de.fungistudii.kalender.client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import de.fungistudii.kalender.client.database.DataRoot;
import de.fungistudii.kalender.client.database.Friseur;
import de.fungistudii.kalender.client.database.Kunde;
import de.fungistudii.kalender.client.database.Termin;
import de.fungistudii.kalender.client.database.NetworkData.*;
import de.fungistudii.kalender.client.database.Service;
import de.fungistudii.kalender.client.listeners.TerminListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

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

        k.register(DataRoot.class);
        k.register(Date.class);
        k.register(Kunde.class);
        k.register(Friseur.class);
        k.register(Service.class);
        k.register(Termin.class);
        k.register(ArrayList.class);
        k.register(LoginRequest.class);
        k.register(LoginResponse.class);
        k.register(TerminRequest.class);
    }
    
    public void registerListeners(){
        super.addListener(new DataListener());
        super.addListener(new LoginListener());
        super.addListener(new TerminListener());
    }
}
