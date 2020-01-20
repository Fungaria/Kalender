package de.fungistudii.kalender.client.listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Friseur;

/**
 *
 * @author sreis
 */
public class VacationListener extends Listener{

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof Friseur){
            Gdx.app.postRunnable(() -> {
                Friseur nuFriseur = (Friseur)object;
                ERE.data.root.friseure.put(nuFriseur.id, nuFriseur);
                ERE.data.writeFile();
                ERE.mainScreen.mitarbeiter.updateContent();
            });
        }
    }
}
