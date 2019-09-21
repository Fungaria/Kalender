/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.account;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.NetworkData.LoginRequest;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author sreis
 */
public class ConnectScreen extends ScreenAdapter {

    private Timer t = new Timer();

    public ConnectScreen() {
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void show() {
        super.show(); //To change body of generated methods, choose Tools | Templates.
    }

    public void connect() {
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    ERE.client.connectToServer();
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            login();
                        }
                    });
                } catch (IOException ex) {
                    System.err.println("failed to connect");
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            ERE.setScreen(new ConnectionFailedScreen(ConnectScreen.this));
                        }
                    });
                }
            }
        }, 200);
    }

    private void login() {
        Preferences prefs = Gdx.app.getPreferences("AccountData");
        if (prefs.getString("username").equals("null")) {
            ERE.setScreen(new LoginScreen());
        } else {
            LoginRequest request = new LoginRequest();
            request.username = prefs.getString("username");
            request.password = prefs.getString("password");

            ERE.client.sendTCP(request);
        }
    }
}
