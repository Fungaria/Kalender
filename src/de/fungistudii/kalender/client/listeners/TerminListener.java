package de.fungistudii.kalender.client.listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.NetworkData;
import de.fungistudii.kalender.database.Termin;


/**
 *
 * @author sreis
 */
public class TerminListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof Termin) {
            addAppointment((Termin) object);
            updateGUI();
        } else if (object instanceof NetworkData.StornoRequest) {
            deleteTermin(((NetworkData.StornoRequest) object).id);
            updateGUI();
        }
    }

    private void updateGUI() {
        Gdx.app.postRunnable(() -> {
            ERE.mainScreen.kalender.updateCurrentTable();
        });
    }

    private void deleteTermin(int id) {
        ERE.data.root.appointments.remove(id);
        ERE.data.writeFile();
    }

    private void addAppointment(Termin termin) {
        ERE.data.root.appointments.put(termin.id, termin);
        ERE.data.writeFile();
    }
}
