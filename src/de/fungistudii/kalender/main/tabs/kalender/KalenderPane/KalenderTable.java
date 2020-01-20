package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import de.fungistudii.kalender.util.ScrollPaneFollower;
import de.fungistudii.kalender.util.AnimationStack;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Customer;
import de.fungistudii.kalender.main.generic.CurrentTimeLine;
import de.fungistudii.kalender.main.generic.GenericSearchField;
import de.fungistudii.kalender.util.Fonts;
import de.fungistudii.kalender.util.NinePatchSolid;
import de.fungistudii.kalender.util.value.ValueUtil;
import de.fungistudii.kalender.util.value.VariableValue;
import java.time.LocalDate;

/**
 *
 * @author sreis
 */
public abstract class KalenderTable extends Table {

    private static final int NUM_ROWS = 16;
    public static final int startTime = 8;
    public static BGPool BG_POOL;

    private KalenderGrid old;
    private KalenderGrid nu;
    private ScrollPane pane;
    private AnimationStack container;

    protected Navigator navigator;
    private ScrollPaneFollower top;
    protected Header header;

    private TimeColumn rightTimeColumn;
    private TimeColumn leftTimeColumn;

    private GenericSearchField<Customer> search;
    public TextButton resizeButton;
    public ViewWidget viewWidget;
    public CurrentTimeLine timeLine;

    public LocalDate currentDate;
    
    private final VariableValue elementHeight = new VariableValue(24);

    public KalenderTable() {
        BG_POOL = new BGPool();

        currentDate = LocalDate.now();
    }

    protected void initGUI() {
        old = createGrid(LocalDate.now());
        container = new AnimationStack(old);

        ScrollPane.ScrollPaneStyle paneStyle = new ScrollPane.ScrollPaneStyle();
        paneStyle.hScroll = new NinePatchSolid(ERE.assets.grey2, 2, 15, 15);
        paneStyle.hScrollKnob = new NinePatchSolid(ERE.assets.grey4, 2, 15, 15);

        pane = new ScrollPane(container, paneStyle);
        pane.getListeners().removeIndex(0);
        pane.setFadeScrollBars(false);
        pane.setVariableSizeKnobs(true);
        pane.setOverscroll(false, true);

        leftTimeColumn = new TimeColumn(Align.right);
        Stack leftStack = new Stack(leftTimeColumn);
        ScrollPaneFollower p1 = new ScrollPaneFollower(leftStack);
        p1.setScrollingDisabled(true, false);
        p1.setParent(pane);
        CurrentTimeLine tl = new CurrentTimeLine.Elbel(elementHeight);
        leftStack.add(tl);

        rightTimeColumn = new TimeColumn(Align.left);
        ScrollPaneFollower p2 = new ScrollPaneFollower(rightTimeColumn);
        p2.setScrollingDisabled(true, false);
        p2.setParent(pane);

        top = new ScrollPaneFollower(header);
        top.setScrollingDisabled(false, true);
        top.setParent(pane);

        resizeButton = new TextButton("100%", new TextButton.TextButtonStyle(ERE.assets.createRounded("outline"), null, null, ERE.assets.fonts.createFont("roboto", 14, Fonts.LIGHT)));
        resizeButton.getStyle().over = ERE.assets.createRounded("outline_over");
        resizeButton.getStyle().fontColor = ERE.assets.grey7;

        search = new GenericSearchField<>((String s, Customer k) -> (k.name.startsWith(s) || (k.vorname + " " + k.name).startsWith(s)));
        search.setMessageText("Kunden suchen ...");
        search.setItems(ERE.data.root.kunden.values());

        timeLine = new CurrentTimeLine.Line(elementHeight);
        container.add(timeLine);

        viewWidget = new ViewWidget();

        Table navig = new Table();
        navig.add(search).uniform().height(55).expandX().left().minWidth(220);
        navig.add(navigator).uniform().height(55).expandX().center().fillX();
        navig.add(viewWidget).uniform().height(55).expandX().right();

        SpriteDrawable separator = ERE.assets.createDrawable("generic/horizontal_separator", ERE.assets.grey3);

        super.add(navig).growX().colspan(3);
        super.row();
        super.add();
        super.add(top).growX().padTop(10).height(50);
        super.row();
        super.add(new Image(separator)).colspan(3).growX().height(1).padLeft(30).padRight(30);
        super.add();
        super.row();
        super.add(p1).growY().width(50);
        super.add(pane).grow();
        super.add(p2).growY().width(50);

        p2.setZIndex(1000);
        p1.setZIndex(1000);
        top.setZIndex(1000);
    }

    protected void initListeners() {
        //resize Listener
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
                    setElementHeight(elementHeight.getValue() * (amount > 0 ? 1.1f : 0.9f));
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
        top.invalidate();
        timeLine.setZIndex(100);
    }

    public void setElementHeight(float eH) {
        resizeButton.setText(((int) (eH / 0.0024f)) / 100 + "%");

        //round
        float nuHeight = ((int) (eH * 2)) / 2;
        //bound
        float min = getHeight() / (16 * 4);
        float max = 40; //willkürlich
        nuHeight = Math.min(Math.max(nuHeight, min), max);
        //apply
        this.elementHeight.setValue(nuHeight);
        old.invalidateHierarchy();
        pane.layout();
        rightTimeColumn.elementHeight.setValue(nuHeight);
        leftTimeColumn.elementHeight.setValue(nuHeight);
        rightTimeColumn.invalidateHierarchy();
        leftTimeColumn.invalidateHierarchy();
    }

    public Value getElementHeight() {
        return elementHeight;
    }

    public void updateCurrentTable() {
        old.reload();
        setElementHeight(elementHeight.get());
    }

    public LocalDate getCurrentDate(){
        return currentDate;
    }
    
    public void switchDate(LocalDate date) {
        int direction = date.compareTo(currentDate);
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
        container.setMainActor(nu);
        nu.setVisible(false);
        nu.buttons.uncheckAll();

        Gdx.app.postRunnable(() -> {
            nu.animateIn(direction > 0, old.getWidth(), (KalenderGrid c) -> {
                old = c;
                nu = null;
            });
            old.animateOut(direction > 0);
        });
    }

    public abstract KalenderGrid createGrid(LocalDate date);

    public BackgroundElement getSelectedElement() {
        return old.getSelectedElement();
    }

    public int getSelectedDuration() {
        return old.getSelectedDuration();
    }

    private static class TimeColumn extends Table {

        public final VariableValue elementHeight = new VariableValue(24);

        private final Drawable timeFiller = ERE.assets.createNinePatchDrawable("kalender/grid/time", 2);

        public TimeColumn(int align) {
            TextButton.TextButtonStyle dateStyle = new TextButton.TextButtonStyle(timeFiller, timeFiller, timeFiller, ERE.assets.fonts.createFont("robotoCondensed", 14));
            dateStyle.fontColor = ERE.assets.grey5;
            for (int i = 0; i < NUM_ROWS; i++) {
                Image image = new Image(timeFiller);
                Label label = new Label(startTime + i + "", new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 15), ERE.assets.grey6));
                if (align == Align.left) {
                    label.setAlignment(Align.bottomLeft);
                    super.add(image).width(20).height(ValueUtil.percentValue(4, elementHeight));
                    super.add(label).growX().padTop(-10).top().padLeft(5);
                } else {
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

        public abstract void setDate(LocalDate time);
    }

    public static abstract class Navigator extends Table {

        public abstract void setDate(LocalDate time);
    }
}
