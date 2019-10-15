package de.fungistudii.kalender.client.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.NetworkData;
import de.fungistudii.kalender.client.database.Friseur;

/**
 *
 * @author sreis
 */
public class VacationListener extends Listener{

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof Friseur){
            Friseur nuFriseur = (Friseur)object;
            ERE.data.root.friseure.remove(nuFriseur.id);
            ERE.data.root.friseure.put(nuFriseur.id, nuFriseur);
            ERE.data.writeFile();
        }
    }
}
