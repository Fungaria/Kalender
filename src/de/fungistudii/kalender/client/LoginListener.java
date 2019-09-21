package de.fungistudii.kalender.client;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.NetworkData.LoginResponse;

/**
 *
 * @author sreis
 */
public class LoginListener extends Listener{
    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof LoginResponse){
            Gdx.app.postRunnable(() -> {
                ERE.setScreen(ERE.mainScreen);
                System.out.println("setScreen");
            });
        }
    }
}
