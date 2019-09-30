package de.fungistudii.kalender.client.listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.NetworkData;
import de.fungistudii.kalender.client.NetworkData.StornoRequest;

/**
 *
 * @author sreis
 */
public class StornoListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof NetworkData.StornoRequest) {
            Gdx.app.postRunnable(() -> {
                writeTerminToDatabase(((StornoRequest) object).id);
                updateGUI();
            });
        }
    }

    private void updateGUI() {
        ERE.mainScreen.kalender.updateCurrentTable();
    }

    private void writeTerminToDatabase(int id) {
        ERE.data.root.termine.removeIf((t) -> t.id == id);
        ERE.data.writeFile();
    }
}
