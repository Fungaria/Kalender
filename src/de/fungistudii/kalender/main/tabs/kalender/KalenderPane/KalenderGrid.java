/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Termin;
import de.fungistudii.kalender.main.generic.ContextMenu;
import de.fungistudii.kalender.main.tabs.kalender.DatePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class KalenderGrid extends Table{
    
    public final int startTime = 8;

    private final int NUM_ROWS = 16, NUM_COLS;
    private static final float spacing = 0.1f;

    public Table[] columns;

    private final SpriteDrawable topElement = ERE.assets.createDrawable("kalender/grid/element_top");
    private final SpriteDrawable bottomElement = ERE.assets.createDrawable("kalender/grid/element_bottom");
    private final SpriteDrawable topFiller = ERE.assets.createDrawable("kalender/grid/filler_top");
    private final SpriteDrawable bottomFiller = ERE.assets.createDrawable("kalender/grid/filler_bottom");

    private Calendar calendar;
    private Date start;

    private final ButtonGroup<Button> buttons = new ButtonGroup<>();

    private DatePicker.DateSelectCallback callback;

    private ContextMenu backgroundContext;
    private ContextMenu terminContext;
    
    public KalenderGrid(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, startTime);
        this.start = calendar.getTime();
        
        buttons.setMinCheckCount(0);
        
        NUM_COLS = ERE.data.root.friseure.size();
        columns = new Table[NUM_COLS];

        for (int i = 0; i < NUM_COLS; i++) {
            columns[i] = new MitarbeiterColumn(i);
        }
        
        for (int i = 0; i < NUM_COLS; i++) {
            add(columns[i]).grow().uniform();
            columns[i].setZIndex(1);
            if (i < NUM_COLS - 1) {
                Table filler = new FillerColumn();
                add(filler).minWidth(10).maxWidth(20);
                filler.setZIndex(0);
            }
        }
    }
    
    public Button getSelectedElement(){
        return buttons.getChecked();
    }
    
    public void reloadTable(){
        for (int i = 0; i < NUM_COLS; i++) {
            reloadColumn(i);
        }
    }
    
    public void reloadColumn(int i){
        Cell<Table> cell = getCell(columns[i]);
        cell.clearActor();
        cell.setActor(new MitarbeiterColumn(i));
    }
    
    private class MitarbeiterColumn extends Table {
        private final SimpleDateFormat compareDayFormat = new SimpleDateFormat("yyyyMMdd");

        public MitarbeiterColumn(int column) {
            calendar.setTime(start);
            Termin[] termine = ERE.data.root.termine.stream().filter((termin) -> (termin.friseur == column && compareDayFormat.format(calendar.getTime()).equals(compareDayFormat.format(termin.start)))).toArray(Termin[]::new);
            int nextIndex = 0;

            for (int row = 0; row < NUM_ROWS * 4; row++) {
                calendar.add(Calendar.MINUTE, 15);
                if (nextIndex < termine.length && termine[nextIndex].start.before(calendar.getTime())) {
                    TerminElement element = new TerminElement(termine[nextIndex], this);
                    int cells = termine[nextIndex].dauer / 15;
                    super.add(element).height(Value.percentHeight(cells, super.getCells().get(0).getActor())).grow();
                    row += cells - 1;
                    buttons.add(element);
                    nextIndex++;
                } else {
                    BackgroundElement element = new BackgroundElement(row, column, row % 4 == 0);
                    buttons.add(element);
                    super.add(element).grow().minSize(0);
                }
                super.row();
            }

            super.setClip(true);
            calendar.setTime(start);
        }
    }

    private class FillerColumn extends Table {

        public FillerColumn() {
            for (int row = 0; row < NUM_ROWS * 4; row++) {
                Image img = new Image(row % 4 == 0 ? topFiller : bottomFiller);
                img.setScaling(Scaling.fillY);
                super.add(img).growX();
                super.row();
            }
        }
    }
}
