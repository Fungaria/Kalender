package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import de.fungistudii.kalender.util.ScrollPaneFollower;
import de.fungistudii.kalender.util.AnimationStack;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.util.DateUtil;
import de.fungistudii.kalender.util.value.ValueUtil;
import de.fungistudii.kalender.util.value.VariableValue;
import java.util.Date;

/**
 *
 * @author sreis
 */
public abstract class KalenderTable extends Table {

    private final int NUM_ROWS = 16;
    public final int startTime = 8;

    private final Drawable timeFiller = ERE.assets.createNinePatchDrawable("kalender/grid/time", 2);

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

    public KalenderTable(Date date, Header header, Navigator navigator) {
        this.header = header;
        this.navigator = navigator;

        old = createGrid(date);
        nu = createGrid(date);

        container = new AnimationStack(old);
        container.add(nu);

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

        super.add();
        super.add(navigator).minWidth(Value.percentWidth(0.3f, this)).height(40);
        super.add();
        super.row();
        super.add();
        super.add(n).growX().padTop(10).height(50);
        super.add();
        super.row();
        super.add(p1).growY();
        super.add(pane).grow();
        super.add(p2).growY();

        p2.setZIndex(1000);
        p1.setZIndex(1000);
        n.setZIndex(1000);

        addListener(desktopListener);
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
    }

    public void setElementHeight(float elementHeight) {
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

    public void updateCurrentTable() {
        container.removeActor(old);
        old = createGrid(currentDate);
        container.setMainActor(old);
        nu.setVisible(false);
    }

    public void switchDate(Date date, int direction) {
        currentDate = date;
        navigator.setDate(date);

        if (direction == 0) {
            updateCurrentTable();
            return;
        }

        nu = createGrid(date);
        nu.elementHeight.setValue(old.elementHeight.getValue());
        container.setMainActor(nu);
        nu.setVisible(false);

        nu.clearActions();
        old.clearActions();

        Gdx.app.postRunnable(() -> {
            nu.setVisible(true);
            nu.setPosition(direction * old.getWidth(), 0);
            old.setPosition(0, 0);
            old.addAction(Actions.sequence(Actions.moveBy(-direction * old.getWidth(), 0, Cons.calendarTransitionTime, Interpolation.pow2), Actions.hide(), Actions.run(() -> {
                container.removeActor(old);
                old = nu;
            })));
            nu.addAction(Actions.moveBy(-direction * nu.getWidth(), 0, Cons.calendarTransitionTime, Interpolation.pow2));
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
        private boolean dragging;
        private Date startRow;
        private int col;

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Actor hit = hit(x, y, true);
            pane.cancel();
            if (hit instanceof GridElement && ((GridElement) hit).isChecked() && old.buttons.getAllChecked().size > 1) {
                return true;
            }
            dragging = true;
            old.buttons.uncheckAll();
            old.buttons.setMaxCheckCount(100);
            if (hit instanceof GridElement) {
                startRow = ((GridElement) hit).getStart();
                col = (((Table) hit.getParent().getParent()).getCell(hit.getParent()).getColumn()) / 2;
            }
            return true;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            old.buttons.setMaxCheckCount(1);
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            dragging = false;
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            if (!dragging) {
                return;
            }
            Actor hit = hit(x, y, true);
            if (hit instanceof GridElement) {
                Date currentRow = ((GridElement) hit).getStart();
                Date min = DateUtil.min(currentRow, startRow);
                Date max = DateUtil.max(currentRow, startRow);
            }
        }
    };

    private class TimeColumn extends Table {

        private final TextButton.TextButtonStyle dateStyle = new TextButton.TextButtonStyle(timeFiller, timeFiller, timeFiller, ERE.assets.fonts.createFont("robotoCondensed", 14));

        public final VariableValue elementHeight = new VariableValue(24);

        public TimeColumn(int align) {
            dateStyle.fontColor = ERE.assets.grey4;
            for (int i = 0; i < NUM_ROWS; i++) {
                TextButton element = new TextButton(startTime + i + "", dateStyle);
                element.getLabel().setAlignment((align | Align.top) & ~Align.bottom);
                element.getLabelCell().pad(6);
                super.add(element).growX().minWidth(Value.percentWidth(0.03f, KalenderTable.this)).height(ValueUtil.percentValue(4, elementHeight));
                super.row();
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
