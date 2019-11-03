/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Blockierung;
import de.fungistudii.kalender.client.database.Termin;
import de.fungistudii.kalender.main.generic.ContextMenu;
import de.fungistudii.kalender.main.generic.DatePicker;
import static de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderTable.pool;
import de.fungistudii.kalender.util.DateUtil;
import de.fungistudii.kalender.util.NinePatchSolid;
import de.fungistudii.kalender.util.StreamUtils;
import de.fungistudii.kalender.util.value.ValueUtil;
import de.fungistudii.kalender.util.value.VariableValue;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;

/**
 *
 * @author sreis
 */
public abstract class KalenderGrid extends Table implements Disposable {

    public final int NUM_ROWS = 16, NUM_COLS;
    private static final float spacing = 0.1f;

    private MitarbeiterColumn[] columns;

    private final Drawable topElement = ERE.assets.createNinePatchDrawable("kalender/grid/element_top", 2);
    private final Drawable bottomElement = ERE.assets.createNinePatchDrawable("kalender/grid/element_bottom", 2);
    private final Drawable topFiller = ERE.assets.createNinePatchDrawable("kalender/grid/filler_top", 2);
    private final Drawable bottomFiller = ERE.assets.createNinePatchDrawable("kalender/grid/filler_bottom", 2);

    protected Calendar calendar;
    public Date start;

    final ButtonGroup<Button> buttons = new ButtonGroup<>();

    private DatePicker.DateSelectCallback callback;

    private ContextMenu backgroundContext;
    private ContextMenu terminContext;

    public final VariableValue elementHeight = new VariableValue(24);

    public KalenderGrid(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, KalenderTable.startTime);
        this.start = calendar.getTime();

        buttons.setMinCheckCount(0);

        NUM_COLS = ERE.data.root.friseure.size();
        columns = new MitarbeiterColumn[NUM_COLS];
    }

    protected void init() {
        for (int i = 0; i < NUM_COLS; i++) {
            columns[i] = createMitarbeiterColumn(i, start);
        }

        for (int i = 0; i < NUM_COLS; i++) {
            add(columns[i]).grow().uniform().minWidth(0);
            columns[i].setZIndex(1);
            if (i < NUM_COLS - 1) {
                Table filler = new FillerColumn();
                add(filler).width(10);
                filler.setZIndex(0);
            }
        }
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
    
    public GridElement getSelectedElement() {
        GridElement lowest = (GridElement)buttons.getChecked();
        for (Button button : buttons.getAllChecked()) {
            if (((GridElement) button).getStart().before(lowest.getStart())) {
                lowest = (GridElement) button;
            }
        }
        return lowest;
    }

    public int getSelectedDuration() {
        return buttons.getAllChecked().size;
    }

    public void updateColumn(int column) {
        Cell<MitarbeiterColumn> cell = getCell(columns[column]);
        cell.clearActor();
        columns[column] = createMitarbeiterColumn(column, start);
        cell.setActor(columns[column]);
    }

    @Override
    public void dispose() {
        for (MitarbeiterColumn column : columns) {
            column.dispose();
        }
    }

    protected abstract MitarbeiterColumn createMitarbeiterColumn(int column, Date date);

    void selectRange(int col, Date min, Date max) {
        for (GridElement element : columns[col].elements) {
            if (!element.getStart().before(min) && !element.getStart().after(max)) {
                ((Button) element).setChecked(true);
            } else {
                ((Button) element).setChecked(false);
            }
        }
    }

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
            for (GridElement element : column.elements) {
                element.setDisabled(true);
            }
        }
    }
    
    void enableInput(){
        for (MitarbeiterColumn column : columns) {
            for (GridElement element : column.elements) {
                element.setDisabled(false);
            }
        }
    }

    public class MitarbeiterColumn extends Stack {

        public Array<GridElement> elements = new Array<>();

        private Table table;

        public MitarbeiterColumn(int friseur, Termin[] termine, Blockierung[] blockierungen, Date startTime) {
            calendar.setTime(startTime);

            int nxtTermin = 0;
            int nxtBlock = 0;

            table = new Table();

            for (int row = 0; row < NUM_ROWS * 4; row++) {
                GridElement element = null;
                if (nxtBlock < blockierungen.length && DateUtil.compareTime(blockierungen[nxtBlock].start, calendar.getTime()) <= 0) {
                    element = new BlockierungElement(blockierungen[nxtBlock]);
                    int cells = blockierungen[nxtBlock].duration;
                    table.add(element).height(ValueUtil.percentValue(cells, elementHeight)).grow();
                    row += cells - 1;
                    calendar.add(Calendar.MINUTE, 15 * (cells - 1));
                    nxtBlock++;
                } else {
                    element = pool.obtain(calendar.getTime(), friseur, row % 4 == 0);
                    table.add(element).minSize(0).height(elementHeight).top();
                }
                buttons.add(element);
                elements.add(element);
                table.row();
                calendar.add(Calendar.MINUTE, 15);
            }

            super.add(table);
            for (Termin termin : termine) {
                TerminWrapper element = new TerminWrapper(termin, elementHeight);
                buttons.add(element.terminElement);
                super.add(element);
            }

            table.setClip(true);
            calendar.setTime(startTime);
        }
        
        @Override
        public void invalidate() {
            if(table != null)
                table.invalidate();
            super.invalidate();
        }
        
        private void dispose() {
            for (GridElement element : elements) {
                element.dispose();
                if (element instanceof BackgroundElement) {
                    pool.free((BackgroundElement) element);
                }
            }
            elements.clear();
        }
    }

    public class FillerColumn extends Table {

        public FillerColumn() {
            for (int row = 0; row < NUM_ROWS * 4; row++) {
                Image img = new Image(row % 4 == 0 ? topFiller : bottomFiller);
                img.setScaling(Scaling.fillY);
                super.add(img).grow().height(elementHeight);
                super.row();
            }
        }
    }

    public class VacationColumn extends MitarbeiterColumn {

        public VacationColumn(int friseur) {
            super(friseur, new Termin[0], new Blockierung[]{new Blockierung(start, NUM_ROWS * 4, friseur, "Urlaub")}, start);
        }
    }
}
