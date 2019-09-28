package de.fungistudii.kalender.main;

import de.fungistudii.kalender.main.tabpane.TabPane;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.fungistudii.kalender.main.tabs.TabPage;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPage;
import de.fungistudii.kalender.main.tabs.kunden.KundenPage;
import de.fungistudii.kalender.main.tabs.mitarbeiter.MitarbeiterPage;
import de.fungistudii.kalender.main.tabs.produkte.ProduktePage;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author sreis
 */
public class MainScreen extends ScreenAdapter {

    public final Stage stage;
    public final Table root;
    private Viewport viewport;

    private Table contentTable;
    private TabPage currentPage;

    public KalenderPage kalender;
    public MitarbeiterPage mitarbeiter;
    public KundenPage kunden;
    public ProduktePage produkte;
    
    private static final boolean printfps = false;

    public MainScreen() {
        
        root = new Table();
        this.viewport = new ScreenViewport();
        stage = new Stage(viewport);
        
        if(printfps){
            Timer t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    System.out.println(fps);
                }
            }, 0, 1000);
        }
    }

    @Override
    public void show() {
        super.show();
        
        Gdx.input.setInputProcessor(stage);

        root.setFillParent(true);
        stage.addActor(root);

        contentTable = new Table();
        currentPage = new TabPage() {
            public void show() {}
            public void hide() {}
            public void resize(int width, int height) {}
        };
        
        kalender = new KalenderPage();
        mitarbeiter = new MitarbeiterPage();
        produkte = new ProduktePage();
        kunden = new KundenPage();

        root.row();
        root.add(new TabPane()).left().height(Value.percentHeight(0.05f, root)).growX().fillY();
        root.row();
        root.add(contentTable).grow();
        root.top();

        setTab(kalender);
        
        Gdx.app.postRunnable(() ->{
            resize(Gdx.graphics.getWidth()+1, Gdx.graphics.getHeight());
        });
    }

    public void setTab(TabPage t) {
        contentTable.clear();
        currentPage.hide();
        contentTable.add(t).grow();
        t.show();
        currentPage = t;
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.clear();
        stage.dispose();
    }

    @Override
    public void hide() {
        super.hide();
        dispose();
    }
    
    float fps = 0;
    boolean debug;

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage.act();
        stage.draw();
        
        fps = 1/delta;
        
        if(Gdx.input.isKeyJustPressed(Keys.F1))
            stage.setDebugAll(debug = !debug);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
        currentPage.resize(width, height);
    }
}