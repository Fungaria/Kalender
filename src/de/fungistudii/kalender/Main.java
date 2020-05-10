package de.fungistudii.kalender;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import de.fungistudii.kalender.util.Assets;
import de.fungistudii.kalender.client.KalenderClient;
import de.fungistudii.kalender.main.MainScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import de.fungistudii.kalender.account.ConnectScreen;
import de.fungistudii.kalender.database.DataHandler;

/**
 * public static o9i6trEhrenvoll.DEINE_MOM_SINGLETONDÄEhrenClient client; That cat was kina cute.
 * @author sreis
 */
public class Main extends Game{

    public MainScreen mainScreen;
    public Assets assets;
    public KalenderClient client;
    
    public static Main ERE;
    public DataHandler data;
    
    @Override
    public void create() {
        
        ERE = this;
        
        assets = new Assets();
        assets.load();
        client = new KalenderClient();
        
        loadDataBase();
        
        ConnectScreen c = new ConnectScreen();
        setScreen(c);
        c.connect();
    }

    @Override
    public void dispose() {
        super.dispose(); //To change body of generated methods, choose Tools | Templates.
        client.close();
        client.stop();
    }

    @Override
    public void render() {
        super.render();
        
        if(Gdx.input.isKeyJustPressed(Keys.F11)){
            mainScreen = new MainScreen();
            setScreen(mainScreen);
        }
    }
    
    private void loadDataBase(){
        data = new DataHandler();
        data.load();
    }
    
}
