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
import de.fungistudii.kalender.account.ConnectScreen;
import de.fungistudii.kalender.client.database.DataHandler;

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
        client = new KalenderClient();
        
        loadDataBase();
        
        mainScreen = new MainScreen();
        ConnectScreen c = new ConnectScreen();
        setScreen(c);
        c.connect();
    }

    @Override
    public void render() {
        super.render(); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void loadDataBase(){
        data = new DataHandler();
        data.load();
    }
    
}
