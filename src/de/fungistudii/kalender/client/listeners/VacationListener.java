package de.fungistudii.kalender.client.listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Employee;

/**
 *
 * @author sreis
 */
public class VacationListener extends Listener{

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof Employee){
            Gdx.app.postRunnable(() -> {
                Employee nuFriseur = (Employee)object;
                ERE.data.root.friseure.put(nuFriseur.id, nuFriseur);
                ERE.data.writeFile();
//                ERE.mainScreen.mitarbeiter.updateContent();
            });
        }
    }
}
