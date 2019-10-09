package de.fungistudii.kalender.client.listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.NetworkData;
import de.fungistudii.kalender.client.database.Termin;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

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
        ERE.data.root.appointments.removeIf((t) -> t.id == id);
        ERE.data.writeFile();
    }

    private void addAppointment(Termin termin) {
        ERE.data.root.appointments.add(termin);
        ERE.data.root.appointments.sort((termin1, termin2) -> {
            if (termin1.start.before(termin2.start)) {
                return -1;
            } else if (termin1.start.after(termin2.start)) {
                return 1;
            }
            return 0;
        });
        ERE.data.writeFile();
    }
}
