package de.fungistudii.kalender.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.DataRoot;

/**
 *
 * @author sreis
 */
public class DataListener extends Listener{
    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof DataRoot){
            ERE.data.setData((DataRoot)object);
        }
    }
}
