/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Blockierung;
import de.fungistudii.kalender.client.database.Termin;
import de.fungistudii.kalender.main.generic.ContextMenu;
import de.fungistudii.kalender.main.generic.DatePicker;
import de.fungistudii.kalender.util.DateUtil;
import de.fungistudii.kalender.util.value.ValueUtil;
import de.fungistudii.kalender.util.value.VariableValue;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sreis
 */
public abstract class KalenderGrid extends Table {

    public final int startTime = 8;

    public final int NUM_ROWS = 16, NUM_COLS;
    private static final float spacing = 0.1f;

    private MitarbeiterColumn[] columns;

    private final Drawable topElement = ERE.assets.createNinePatchDrawable("kalender/grid/element_top", 2);
    private final Drawable bottomElement = ERE.assets.createNinePatchDrawable("kalender/grid/element_bottom", 2);
    private final Drawable topFiller = ERE.assets.createNinePatchDrawable("kalender/grid/filler_top", 2);
    private final Drawable bottomFiller = ERE.assets.createNinePatchDrawable("kalender/grid/filler_bottom", 2);

    protected Calendar calendar;
    protected Date start;

    final ButtonGroup<GridElement> buttons = new ButtonGroup<>();

    private DatePicker.DateSelectCallback callback;

    private ContextMenu backgroundContext;
    private ContextMenu terminContext;

    public final VariableValue elementHeight = new VariableValue(24);

    public KalenderGrid(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, startTime);
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

    public Button getSelectedElement() {
        GridElement lowest = buttons.getChecked();
        for (GridElement button : buttons.getAllChecked()) {
            if (button.getStart().before(lowest.getStart())) {
                lowest = button;
            }
        }
        return lowest;
    }

    public int getSelectedDuration() {
        return buttons.getAllChecked().size;
    }

    protected abstract MitarbeiterColumn createMitarbeiterColumn(int column, Date date);

    public class MitarbeiterColumn extends Table {

        public Array<GridElement> elements = new Array<>();

        public MitarbeiterColumn(int friseur, Termin[] termine, Blockierung[] blockierungen, Date startTime) {
            calendar.setTime(startTime);

            int nxtTermin = 0;
            int nxtBlock = 0;

            for (int row = 0; row < NUM_ROWS * 4; row++) {
                GridElement element = null;
                if (nxtTermin < termine.length && !termine[nxtTermin].start.after(calendar.getTime())) {
                    element = new TerminElement(termine[nxtTermin]);
                    int cells = termine[nxtTermin].dauer / 15;
                    super.add(element).height(ValueUtil.percentValue(cells, elementHeight)).grow();
                    row += cells - 1;
                    calendar.add(Calendar.MINUTE, 15 * (cells - 1));
                    nxtTermin++;
                } else if (nxtBlock < blockierungen.length && DateUtil.compareTime(blockierungen[nxtBlock].start, calendar.getTime()) <= 0) {
                    element = new BlockierungElement(blockierungen[nxtBlock]);
                    int cells = blockierungen[nxtBlock].duration;
                    super.add(element).height(ValueUtil.percentValue(cells, elementHeight)).grow();
                    row += cells - 1;
                    calendar.add(Calendar.MINUTE, 15 * (cells - 1));
                    nxtBlock++;
                } else {
                    element = new BackgroundElement(calendar.getTime(), friseur, row % 4 == 0);
                    super.add(element).growX().minSize(0).height(elementHeight).top();
                }
                buttons.add(element);
                elements.add(element);
                super.row();
                calendar.add(Calendar.MINUTE, 15);
            }

            super.setClip(true);
            calendar.setTime(startTime);
        }

        public void checkRange(Date begin, Date end) {
            for (GridElement element : elements) {
                if (!element.getStart().before(begin) && !element.getStart().after(end)) {
                    ((Button) element).setChecked(true);
                } else {
                    ((Button) element).setChecked(false);
                }
            }
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
            super(friseur, new Termin[0], new Blockierung[]{new Blockierung(start, NUM_ROWS*4, friseur, "Urlaub")}, start);
        }
    }
}
