package de.fungistudii.kalender.main;

import de.fungistudii.kalender.main.tabpane.TabPane;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.fungistudii.kalender.Cons;
import de.fungistudii.kalender.util.DialogManager;
import de.fungistudii.kalender.main.kalender.KalenderPage;
import de.fungistudii.kalender.main.kunden.CustomerPage;
import de.fungistudii.kalender.main.mitarbeiter.EmployeePage;
import de.fungistudii.kalender.main.produkte.ProduktePage;
import de.fungistudii.kalender.main.servies.ServicePage;

/**
 *
 * @author sreis
 */
public class MainScreen extends ScreenAdapter {

    public final Stage stage;
    public final Table root;
    private ScreenViewport viewport;

    private Table contentTable;
    private TabPage currentPage;

    public KalenderPage kalender;
    public EmployeePage mitarbeiter;
    public CustomerPage kunden;
    public ProduktePage produkte;
    public ServicePage service;

    private boolean printfps = false;

    private final ContextMenuManager contextManager;
    public final DialogManager dialogManager;

    public MainScreen() {
        root = new Table();
        this.viewport = new ScreenViewport();
        stage = new Stage(viewport);

        //unfocus Actors when user clicks anywhere. By default Actors are only unfocused when another Actor "claims" the Keyboard Focus
        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (stage.getKeyboardFocus() != null && !stage.getKeyboardFocus().isAscendantOf(event.getTarget())) {
                    stage.setKeyboardFocus(null);
                }
                return false;
            }
        });

        contextManager = new ContextMenuManager();
        dialogManager = new DialogManager();
    }

    @Override
    public void show() {
        super.show();

        Gdx.input.setInputProcessor(stage);

        root.setFillParent(true);
        stage.addActor(root);

        contentTable = new Table();
        currentPage = new TabPage() {
            public void show() {
            }

            public void hide() {
            }

            public void resize(int width, int height) {
            }
        };

        kalender = new KalenderPage();
        mitarbeiter = new EmployeePage();
        produkte = new ProduktePage();
        service = new ServicePage();
        kunden = new CustomerPage();

        root.row();
        root.add(new TabPane()).left().height(Cons.tabPaneHeight).growX().fillY();
        root.row();
        root.add(contentTable).grow();
        root.top();

        root.setRound(true);

        setTab(kalender);

        contextManager.init();
        stage.addActor(dialogManager);

        Gdx.app.postRunnable(() -> {
            resize(Gdx.graphics.getWidth() + 1, Gdx.graphics.getHeight());
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
    float timer = 0;
    boolean debug;

    @Override
    public void render(float delta) {
        super.render(delta);
        
        Gdx.gl.glClearColor(1, 1, 1, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage.act();
        stage.draw();

        fps = 1 / delta;
        timer += fps;
        if (printfps && timer > 0.5f) {
            System.out.println(fps);
            timer = 0;
        }

        if (Gdx.input.isKeyJustPressed(Keys.F1)) {
            stage.setDebugAll(debug = !debug);
        }
        if (Gdx.input.isKeyJustPressed(Keys.F2)) {
            printfps = !printfps;
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
        currentPage.resize(width, height);
    }

}
