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
            Gdx.app.postRunnable(() -> {
                writeTerminToDatabase((Termin) object);
                addTerminToGUI();
            });
        }
    }

    private void addTerminToGUI() {
        ERE.mainScreen.kalender.updateCurrentTable();
    }

    private void writeTerminToDatabase(Termin termin) {
        ERE.data.root.termine.add(termin);
        ERE.data.root.termine.sort(comparator);
        ERE.data.writeFile();
    }

    private final Comparator<Termin> comparator = new Comparator<Termin>() {
        @Override
        public int compare(Termin o1, Termin o2) {
            if (o1.start.before(o2.start)) {
                return -1;
            } else if (o1.start.after(o2.start)) {
                return 1;
            }
            return 0;
        }
    };
}
