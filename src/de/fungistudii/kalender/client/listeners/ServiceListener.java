package de.fungistudii.kalender.client.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Service;

/**
 *
 * @author sreis
 */
public class ServiceListener extends Listener{

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof Service){
            ERE.data.root.services.put(((Service) object).id, (Service)object);
            ERE.data.writeFile();
            ERE.mainScreen.service.updateContent();
        }
    }
}
