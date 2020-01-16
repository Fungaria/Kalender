/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Disposable;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Blockierung;
import de.fungistudii.kalender.client.database.Termin;
import de.fungistudii.kalender.main.generic.ContextMenu;
import de.fungistudii.kalender.main.generic.DatePicker;
import de.fungistudii.kalender.util.DateUtil;
import de.fungistudii.kalender.util.value.VariableValue;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

/**
 *
 * @author sreis
 */
public abstract class KalenderGrid extends Table implements Disposable {

    private static final float spacing = 0.1f;

    public MitarbeiterColumn[] columns;

    private final Drawable topElement = ERE.assets.createNinePatchDrawable("kalender/grid/element_top", 2);
    private final Drawable bottomElement = ERE.assets.createNinePatchDrawable("kalender/grid/element_bottom", 2);
    private final Drawable topFiller = ERE.assets.createNinePatchDrawable("kalender/grid/filler_top", 2);
    private final Drawable bottomFiller = ERE.assets.createNinePatchDrawable("kalender/grid/filler_bottom", 2);

    protected Calendar calendar;
    public Date start;

    final ButtonGroup<BackgroundElement> buttons = new ButtonGroup<>();

    private DatePicker.DateSelectCallback callback;

    private ContextMenu backgroundContext;
    private ContextMenu terminContext;

    private final Value elementHeight;

    public KalenderGrid(Date date, Value elementHeight) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, KalenderTable.startTime);
        this.start = calendar.getTime();
        
        this.elementHeight = elementHeight;

        buttons.setMinCheckCount(0);
        buttons.setMaxCheckCount(-1);

        columns = new MitarbeiterColumn[ERE.data.root.friseure.size()];

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (button == Buttons.LEFT) {
                    buttons.uncheckAll();
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        addListener(dragListener);
    }

    protected void init() {
        for (int i = 0; i < columns.length; i++) {
            columns[i] = new MitarbeiterColumn(start, elementHeight);
            columns[i].initialize(getFriseur(i), getTermine(i, start), getBlockierungen(i, start), start);
            for (BackgroundElement bg : columns[i].backgroundElements) {
                buttons.add(bg);
            }
        }

        setRound(false);

        SpriteDrawable vs = ERE.assets.createDrawable("generic/vertical_separator", ERE.assets.grey2);
        for (int i = 0; i < columns.length; i++) {
            add(new Image(vs)).width(1).fillY();
            add(columns[i]).minWidth(0).expand().fill().uniform();
            columns[i].setZIndex(1);
        }
        add(new Image(vs)).width(1).fillY();
    }

    @Override
    public void invalidate() {
        for (Actor actor : getChildren()) {
            if (actor instanceof Layout) {
                ((Layout) actor).invalidate();
            }
        }
        super.invalidate();
        //just pack vertically
        setHeight(getPrefHeight());
        validate();
        setHeight(getPrefHeight());
        validate();
    }

    public BackgroundElement getSelectedElement() {
        BackgroundElement lowest = buttons.getChecked();
        for (BackgroundElement button : buttons.getAllChecked()) {
            if (button.getRow() < lowest.getRow()) {
                lowest = button;
            }
        }
        return lowest;
    }

    public int getSelectedDuration() {
        return buttons.getAllChecked().size;
    }

    public void updateColumn(int column) {
        columns[column].clearElements();
        columns[column].initialize(column, getTermine(column, start), getBlockierungen(column, start), start);
    }

    @Override
    public void dispose() {
        for (MitarbeiterColumn column : columns) {
            column.dispose();
        }
    }

    protected abstract Termin[] getTermine(int column, Date date);

    protected abstract Blockierung[] getBlockierungen(int column, Date date);

    protected abstract int getFriseur(int column);

    public void animateIn(boolean direction, float width, Consumer<KalenderGrid> consumer) {
        setVisible(true);
        setPosition((direction ? 1 : -1) * width, 0);
        addAction(Actions.sequence(Actions.moveTo(0, 0, Cons.calendarTransitionTime, Interpolation.pow2), Actions.run(() -> {
            consumer.accept(this);
        })));
    }

    public void animateOut(boolean direction) {
        setPosition(0, 0);
        addAction(Actions.sequence(Actions.moveBy((direction ? -1 : 1) * getWidth(), 0, Cons.calendarTransitionTime, Interpolation.pow2), Actions.hide(), Actions.run(() -> {
            remove();
            dispose();
        })));
    }

    public void skipOut() {
        clearActions();
        remove();
        dispose();
    }

    public void skipIn() {
        clearActions();
        setPosition(0, 0);
    }

    void disableInput() {
        for (MitarbeiterColumn column : columns) {
            for (BackgroundElement element : column.backgroundElements) {
                element.setDisabled(true);
            }
        }
    }

    void enableInput() {
        for (MitarbeiterColumn column : columns) {
            for (BackgroundElement element : column.backgroundElements) {
                element.setDisabled(false);
            }
        }
    }

    private final DragListener dragListener = new DragListener(){
        //drag and drop
        private int col;
        private float actorY;
        private Date startTime;
        private int startRow;
        private final Vector2 tmpVec = new Vector2();

        private DragWrapper dragged;
        private boolean dragging;

        @Override
        public void dragStart(InputEvent event, float x, float y, int pointer) {
            Actor hit = event.getTarget();
            if (!(hit instanceof GridElement) || !((GridElement)hit).isFocused()) {
                return;
            }

            GridElement element = (GridElement) hit;
            element.fadeOut();
            startTime = element.getStart();

            dragged = new DragWrapper(element);
            addActor(dragged);

            actorY = hit.localToActorCoordinates(KalenderGrid.this, tmpVec.set(0, 0)).y;
            dragged.jumpTo(tmpVec.x, tmpVec.y);
            disableInput();

            startRow = columns[0].table.getRow(y);

            dragging = true;
        }

        @Override
        public void dragStop(InputEvent event, float x, float y, int pointer) {
            if(!dragging)
                return;
            
            enableInput();
            Date start = dragged.getGridElement().getStart();
            dragged.getGridElement().setFriseur(col);
            ERE.client.sendTCP(dragged.getGridElement().serialize());
            
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    dragged.dispose();
                }
            }, 100);
        }

        @Override
        public void drag(InputEvent event, float x, float y, int pointer) {
            if (!dragging)
                return;
            
            col = (int) (x / columns[0].getWidth());
            float targetX = columns[col].getX();
            int distanceY = startRow - columns[0].table.getRow(y);
            dragged.getGridElement().setStart(DateUtil.add(startTime, Calendar.MINUTE, -15 * distanceY));
            dragged.setPosition(targetX, actorY + elementHeight.get() * distanceY);
        }
    };

    public class VacationColumn extends MitarbeiterColumn {

        public VacationColumn(int friseur) {
            super(start, elementHeight);
            super.initialize(friseur, new Termin[0], new Blockierung[]{new Blockierung(start, NUM_ROWS * 4, friseur, "Urlaub")}, start);
        }
    }
}
