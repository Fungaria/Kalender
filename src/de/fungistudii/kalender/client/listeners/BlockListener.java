package de.fungistudii.kalender.client.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Blockierung;

/**
 *
 * @author sreis
 */
public class BlockListener extends Listener{

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof Blockierung){
            ERE.data.root.blockierungen.add((Blockierung)object);
            ERE.data.writeFile();
            ERE.mainScreen.kalender.updateCurrentTable();
        }
    }
}
