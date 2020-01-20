package de.fungistudii.kalender.client;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.NetworkData.LoginResponse;
import de.fungistudii.kalender.main.MainScreen;

/**
 *
 * @author sreis
 */
public class LoginListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof LoginResponse) {
            Gdx.app.postRunnable(() -> {
                ERE.mainScreen = new MainScreen();
                ERE.setScreen(ERE.mainScreen);
            });
        }
    }
}
