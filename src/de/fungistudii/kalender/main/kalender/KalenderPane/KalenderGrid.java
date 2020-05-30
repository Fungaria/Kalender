/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.kalender.KalenderPane;

import com.badlogic.gdx.Gdx;
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
import de.fungistudii.kalender.database.Blockierung;
import de.fungistudii.kalender.database.Termin;
import de.fungistudii.kalender.main.generic.ContextMenu;
import de.fungistudii.kalender.main.generic.datepicker.DatePicker;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    protected LocalDate date;

    final ButtonGroup<BackgroundElement> buttons = new ButtonGroup<>();

    private DatePicker.DateSelectCallback callback;

    private ContextMenu backgroundContext;
    private ContextMenu terminContext;

    private final Value elementHeight;

    public KalenderGrid(LocalDate date, Value elementHeight) {
        this.date = date;

        this.elementHeight = elementHeight;

        buttons.setMinCheckCount(0);
        buttons.setMaxCheckCount(-1);

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
            columns[i] = new MitarbeiterColumn(getStartTime(i, date), elementHeight);
            columns[i].initialize(getFriseur(i), getTermine(i, date), getBlockierungen(i, date));
            
            for (BackgroundElement bg : columns[i].backgroundElements) {
                buttons.add(bg);
            }
        }
        
        setRound(true);

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
        columns[column].initialize(column, getTermine(column, date), getBlockierungen(column, date));
    }

    @Override
    public void dispose() {
        for (MitarbeiterColumn column : columns) {
            column.dispose();
        }
    }

    protected abstract Termin[] getTermine(int column, LocalDate date);

    protected abstract Blockierung[] getBlockierungen(int column, LocalDate date);

    protected abstract int getFriseur(int column);
    
    protected abstract LocalDateTime getStartTime(int column, LocalDate date);

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

    private DragWrapper dragged;
    private final DragListener dragListener = new DragListener() {
        //drag and drop
        private int col;
        private float actorY;
        private LocalDateTime startTime;
        private int startRow;
        private final Vector2 tmpVec = new Vector2();

        private boolean dragging;

        @Override
        public void dragStart(InputEvent event, float x, float y, int pointer) {
            Actor hit = event.getTarget();
            if (!(hit instanceof GridElement) || !((GridElement) hit).isFocused()) {
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
            if (!dragging) {
                return;
            }

            enableInput();
            LocalDateTime start = dragged.getGridElement().getStart();
            dragged.getGridElement().setFriseur(col);

            dragging = false;
            ERE.client.sendTCP(dragged.getGridElement().serialize());
        }

        @Override
        public void drag(InputEvent event, float x, float y, int pointer) {
            if (!dragging) {
                return;
            }

            col = (int) (x / columns[0].getWidth());
            float targetX = columns[col].getX();
            int distanceY = startRow - columns[0].table.getRow(y);
            dragged.getGridElement().setStart(startTime.minusMinutes(15 * distanceY));
            dragged.setPosition(targetX, actorY + elementHeight.get() * distanceY);
        }
    };

    public void reload() {
        if(dragged != null){
            dragged.dispose();
            dragged = null;
        }
        for (int i = 0; i < columns.length; i++) {
            updateColumn(i);
        }
    }

    public class VacationColumn extends MitarbeiterColumn {
        public VacationColumn(int friseur) {
            super(getStartTime(friseur, date), elementHeight);
//            super.initialize(friseur, new Termin[0], new Blockierung[]{new Blockierung(date, NUM_ROWS * 4, friseur, "Urlaub")});
        }
    }
}