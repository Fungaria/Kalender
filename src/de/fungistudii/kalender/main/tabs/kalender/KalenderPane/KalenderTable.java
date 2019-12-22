package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import de.fungistudii.kalender.util.ScrollPaneFollower;
import de.fungistudii.kalender.util.AnimationStack;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.tabs.kalender.TerminElement;
import de.fungistudii.kalender.util.DateUtil;
import de.fungistudii.kalender.util.DrawableSolid;
import de.fungistudii.kalender.util.Fonts;
import de.fungistudii.kalender.util.NinePatchSolid;
import de.fungistudii.kalender.util.value.ValueUtil;
import de.fungistudii.kalender.util.value.VariableValue;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sreis
 */
public abstract class KalenderTable extends Table {

    private final int NUM_ROWS = 16;

    private KalenderGrid old;
    private KalenderGrid nu;
    private ScrollPane pane;
    private AnimationStack container;

    private TimeColumn leftTimeColumn;
    private TimeColumn rightTimeColumn;

    private Date currentDate;

    protected Header header;
    protected Navigator navigator;

    private ScrollPaneFollower n;

    private float elementHeight = 24;

    public static final int startTime = 8;

    public static BGPool pool;

    public TextButton resizeButton;
    public ViewWidget viewWidget;

    public KalenderTable(Date date, Header header, Navigator navigator) {
        pool = new BGPool();
        this.header = header;
        this.navigator = navigator;

        old = createGrid(date);
        container = new AnimationStack(old);

        pane = new ScrollPane(container);
        pane.setOverscroll(false, true);

        leftTimeColumn = new TimeColumn(Align.left);
        ScrollPaneFollower p2 = new ScrollPaneFollower(leftTimeColumn);
        p2.setScrollingDisabled(true, false);
        p2.setParent(pane);

        rightTimeColumn = new TimeColumn(Align.right);
        ScrollPaneFollower p1 = new ScrollPaneFollower(rightTimeColumn);
        p1.setScrollingDisabled(true, false);
        p1.setParent(pane);

        n = new ScrollPaneFollower(header);
        n.setScrollingDisabled(false, true);
        n.setParent(pane);

        resizeButton = new TextButton("100%", new TextButton.TextButtonStyle(ERE.assets.createRounded("outline"), null, null, ERE.assets.fonts.createFont("roboto", 14, Fonts.LIGHT)));
        resizeButton.getStyle().over = ERE.assets.createRounded("outline_over");
        resizeButton.getStyle().fontColor = ERE.assets.grey7;

        KalSearch search = new  KalSearch();
        
        viewWidget = new ViewWidget();  

        Table navig = new Table();
        navig.add(search).uniform().height(55).expandX().left().minWidth(220);
        navig.add(navigator).uniform().height(55).expandX().center().fillX();
        navig.add(viewWidget).uniform().height(55).expandX().right();

        SpriteDrawable separator = ERE.assets.createDrawable("generic/horizontal_separator", ERE.assets.grey3);

        super.add(navig).growX().colspan(3);
        super.row();
        super.add();
        super.add(n).growX().padTop(10).height(50);
        super.row();
        super.add(new Image(separator)).colspan(3).growX().height(1).padLeft(30).padRight(30);
        super.add();
        super.row();
        super.add(p1).growY().width(50);
        super.add(pane).grow();
        super.add(p2).growY().width(50);

        p2.setZIndex(1000);
        p1.setZIndex(1000);
        n.setZIndex(1000);

        super.addListener(desktopListener);
        ERE.mainScreen.stage.addCaptureListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Keys.CONTROL_LEFT) {
                    ERE.mainScreen.stage.setScrollFocus(null);
                    pane.cancel();
                    return true;
                }
                return false;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Keys.CONTROL_LEFT) {
                    ERE.mainScreen.stage.setScrollFocus(pane);
                    return true;
                }
                return false;
            }

            @Override
            public boolean scrolled(InputEvent event, float x, float y, int amount) {
                if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
                    setElementHeight(old.elementHeight.getValue() * (amount > 0 ? 1.1f : 0.9f));
                }
                return super.scrolled(event, x, y, amount);
            }
        });
        resizeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setElementHeight(24);
            }
        });
    }

    @Override
    public float getPrefWidth() {
        return 2000;
    }

    public void setElementHeight(float elementHeight) {
        resizeButton.setText(((int) (elementHeight / 0.0024f)) / 100 + "%");
        this.elementHeight = elementHeight;
        float min = getHeight() / (old.NUM_ROWS * 4);
        float max = 40; //willkürlich
        float height = Math.min(Math.max(elementHeight, min), max);
        old.elementHeight.setValue(height);
        old.invalidateHierarchy();
        pane.layout();
        leftTimeColumn.elementHeight.setValue(height);
        rightTimeColumn.elementHeight.setValue(height);
        leftTimeColumn.invalidateHierarchy();
        rightTimeColumn.invalidateHierarchy();
    }

    public float getElementHeight() {
        return elementHeight;
    }

    @Override
    public void layout() {
        super.layout();
        if (pane.getWidth() < 1000) {
            pane.setScrollingDisabled(false, false);
        } else {
            pane.setScrollingDisabled(true, false);
        }
        header.updateLayout(old);
        header.invalidate();
        n.invalidate();
    }

    @Override
    public void act(float delta) {
        super.act(delta); //To change body of generated methods, choose Tools | Templates.
    }

    public void updateCurrentTable() {
        old.remove();
        old = createGrid(currentDate);
        container.setMainActor(old);
        setElementHeight(elementHeight);
    }

    public void switchDate(Date date, int direction) {
        currentDate = date;
        navigator.setDate(date);

        if (direction == 0) {
            updateCurrentTable();
            return;
        }

        if (nu != null) {
            nu.skipIn();
            old.skipOut();
            old = nu;
            nu = null;
        }

        nu = createGrid(date);
        nu.elementHeight.setValue(old.elementHeight.getValue());
        container.setMainActor(nu);
        nu.setVisible(false);

        Gdx.app.postRunnable(() -> {
            nu.animateIn(direction > 0, old.getWidth(), (c) -> {
                old = c;
                nu = null;
            });
            old.animateOut(direction > 0);
        });
    }

    public abstract KalenderGrid createGrid(Date date);

    public Button getSelectedElement() {
        return old.getSelectedElement();
    }

    public int getSelectedDuration() {
        return old.getSelectedDuration();
    }

    private ClickListener desktopListener = new ClickListener() {
        //selecting
        private boolean selecting;
        private Date startRow;
        private int col;

        //drag and drop
        private float startY;
        private float actorY;
        private int distanceY;
        private final Vector2 tmpVec = new Vector2();

        private TerminDD dragged;

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Actor hit = hit(x, y, true);
            pane.cancel();

            if (hit instanceof GridElement && ((Button) hit).isChecked() && old.buttons.getAllChecked().size > 1) {
                return true;
            }
            if (hit instanceof TerminElement && ((TerminElement) hit).isChecked()) {
                ((TerminElement) hit).fadeOut();
                dragged = new TerminDD((TerminElement) hit, old.elementHeight);
                old.addActor(dragged);
                startY = y; distanceY = 0; col = dragged.getTermin().friseur;
                actorY = hit.localToActorCoordinates(old, tmpVec.set(0, 0)).y;
                dragged.jumpTo(tmpVec.x, tmpVec.y);
                old.disableInput();
            } else if (hit instanceof GridElement) {
                old.buttons.uncheckAll();
                startRow = ((GridElement) hit).getStart();
                col = ((GridElement) hit).getFriseur();
            }
            if (hit instanceof BackgroundElement) {
                selecting = true;
                old.buttons.setMaxCheckCount(100);
            }
            return true;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            old.buttons.setMaxCheckCount(1);
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            selecting = false;
            // release Dragged Termin
            if (dragged != null) {
                old.enableInput();
                dragged.getTermin().start = DateUtil.add(dragged.getTermin().start, Calendar.MINUTE, -15 * distanceY);
                dragged.getTermin().friseur = col;
                ERE.client.sendTCP(dragged.getTermin());
                dragged.dispose();
                dragged = null;
            }
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            Actor hit = hit(x, y, true);
            if (selecting && hit instanceof GridElement) {
                Date currentRow = ((GridElement) hit).getStart();
                Date min = DateUtil.min(currentRow, startRow);
                Date max = DateUtil.max(currentRow, startRow);
                old.selectRange(col, min, max);
            } else if (dragged != null && hit instanceof GridElement) {
                float targetX = (hit.getParent()).localToActorCoordinates(old, tmpVec.set(0, 0)).x;
                col = ((GridElement) hit).getFriseur();
                distanceY = (int) ((y-startY)/old.elementHeight.get());
                dragged.setPosition(targetX,actorY + old.elementHeight.get() * distanceY);
            }
        }
    };

    private class TimeColumn extends Table {

        public final VariableValue elementHeight = new VariableValue(24);

        private final Drawable timeFiller = ERE.assets.createNinePatchDrawable("kalender/grid/time", 2);

        public TimeColumn(int align) {
            TextButton.TextButtonStyle dateStyle = new TextButton.TextButtonStyle(timeFiller, timeFiller, timeFiller, ERE.assets.fonts.createFont("robotoCondensed", 14));
            dateStyle.fontColor = ERE.assets.grey5;
            for (int i = 0; i < NUM_ROWS; i++) {
                Image image = new Image(timeFiller);
                Label label = new Label(startTime+i+"", new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 15), ERE.assets.grey6));
                if(align == Align.left){
                    label.setAlignment(Align.bottomLeft);
                    super.add(image).width(20).height(ValueUtil.percentValue(4, elementHeight));
                    super.add(label).growX().padTop(-10).top().padLeft(5);
                }else{
                    label.setAlignment(Align.bottomRight);
                    super.add(label).growX().padTop(-10).top().padRight(5);
                    super.add(image).width(20).height(ValueUtil.percentValue(4, elementHeight));
                }
                super.row();
                super.setClip(false);
            }
        }
    }

    public static abstract class Header extends Table {

        public abstract void updateLayout(KalenderGrid grid);

        public abstract void setDate(Date time);
    }

    public static abstract class Navigator extends Table {

        public abstract void setDate(Date time);
    }
}
