/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import de.fungistudii.kalender.client.database.Blockierung;
import de.fungistudii.kalender.client.database.Termin;
import de.fungistudii.kalender.util.DateUtil;
import java.util.Calendar;
import java.util.Date;
import static de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderTable.BG_POOL;

/**
 *
 * @author sreis
 */
public class MitarbeiterColumn extends Stack implements Disposable {

    public Array<BackgroundElement> backgroundElements = new Array<>();
    Table table;

    public static final int NUM_ROWS = 16;
    private final Value elementHeight;

    public MitarbeiterColumn(Date start, Value elementHeight) {
        this.elementHeight = elementHeight;
        table = new Table();
        table.addListener(selectListener);
        super.add(table);

        DateUtil.calendar.setTime(start);
        for (int row = 0; row < NUM_ROWS * 4; row++) {
            BackgroundElement element = BG_POOL.obtain(DateUtil.calendar.getTime(), row);
            backgroundElements.add(element);
            table.add(element).minSize(0).height(elementHeight).top().grow();
            table.row();
            DateUtil.calendar.add(Calendar.MINUTE, 15);
        }
    }

    public void initialize(int friseur, Termin[] termine, Blockierung[] blockierungen, Date startTime) {
        for (BackgroundElement element : backgroundElements) {
            element.setFriseur(friseur);
        }

        for (Blockierung block : blockierungen) {
            ElementWrapper<BlockElement> element = new ElementWrapper<>(new BlockElement(block), elementHeight);
            element.get().addHandleListener(this);
            super.add(element);
        }

        for (Termin termin : termine) {
            ElementWrapper element = new ElementWrapper<>(new TerminElement(termin), elementHeight);
            element.get().addHandleListener(this);
            super.add(element);
        }
    }

    @Override
    public void invalidate() {
        if (table != null) {
            table.invalidate();
        }
        super.invalidate();
    }

    public void dispose() {
        for (BackgroundElement element : backgroundElements) {
            element.dispose();
            if (element instanceof BackgroundElement) {
                BG_POOL.free((BackgroundElement) element);
            }
        }
        backgroundElements.clear();
    }

    void selectRange(BackgroundElement start, int range) {
        int min = Math.min(start.getRow(), start.getRow() + range);
        int max = Math.max(start.getRow(), start.getRow() + range);
        for (BackgroundElement element : backgroundElements) {
            if (element.getRow() <= max && element.getRow() >= min) {
                ((Button) element).setChecked(true);
            } else {
                ((Button) element).setChecked(false);
            }
        }
    }

    void deselect() {
        backgroundElements.forEach(e -> e.setChecked(false));
    }

    private DragListener selectListener = new DragListener() {
        private boolean selecting;
        private BackgroundElement start;
        private int startY;
        private final Vector2 tmpVec = new Vector2();

        @Override
        public void dragStart(InputEvent event, float x, float y, int pointer) {
            Actor hit = event.getTarget();

            if (hit instanceof BackgroundElement) {
                selecting = true;
                start = (BackgroundElement) hit;
                startY = ((BackgroundElement) hit).getRow();
            }
        }

        @Override
        public void dragStop(InputEvent event, float x, float y, int pointer) {
            selecting = false;
            start.setChecked(true);
        }

        @Override
        public void drag(InputEvent event, float x, float y, int pointer) {
            if (selecting) {
                selectRange(start, table.getRow(y) - startY);
            }
        }
    };

    void clearElements() {
        clearChildren();
        add(table);
    }
}
