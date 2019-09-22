package de.fungistudii.kalender.client.listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Termin;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author sreis
 */
public class TerminListener extends Listener{
    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof Termin){
            Gdx.app.postRunnable(() -> {
                writeTerminToDatabase((Termin)object);
                addTerminToGUI((Termin)object);
            });
        }
    }
    
    private void addTerminToGUI(Termin termin){
        ERE.mainScreen.kalender.addTermin(termin);
    }
    
    private void writeTerminToDatabase(Termin termin){
        ERE.data.root.termine.add(termin);
        ERE.data.root.termine.sort(comparator);
        try {
            ERE.data.writeFile();
        } catch (JAXBException ex) {
            Logger.getLogger(TerminListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private final Comparator<Termin> comparator = new Comparator<Termin>() {
        @Override
        public int compare(Termin o1, Termin o2) {
            if(o1.start.before(o2.start))
                return -1;
            else if(o1.start.after(o2.start))
                return 1;
            return 0;
        }
    };
}
